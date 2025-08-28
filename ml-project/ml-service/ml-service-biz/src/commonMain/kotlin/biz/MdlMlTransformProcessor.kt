package api.kotlinproject.biz

import api.kotlinproject.biz.general.initStatus
import api.kotlinproject.biz.general.operation
import api.kotlinproject.biz.general.stubs
import api.kotlinproject.biz.repo.*
import api.kotlinproject.biz.stubs.stubDbError
import api.kotlinproject.biz.stubs.stubNoCase
import api.kotlinproject.biz.trainmodel.trainModelTransform
import api.kotlinproject.biz.trainmodel.training
import api.kotlinproject.biz.validation.finishMlValidationTransform
import api.kotlinproject.biz.validation.validation
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.MdlTransformCorrSettings
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.cor.chain
import api.kotlinproject.cor.rootChain
import api.kotlinproject.cor.worker
import biz.stubs.stubTransformSuccess
import biz.stubs.stubValidationBadTransform
import biz.trainmodel.initTrainModelTransform
import biz.validation.validateTransformHasContent

class MdlMlTransformProcessor(
    private val corSettingsTransform: MdlTransformCorrSettings = MdlTransformCorrSettings.NONE
) {
    suspend fun exec(ctx: MdlContext) = businessChain.exec(ctx.also
    {
        it.corSettingsTransform = corSettingsTransform
    })
    private val businessChain = rootChain<MdlContext> {
        initStatus("Инициализация статуса")
        initRepoTransform("Инициализация репозитория")
        initTrainModelTransform("Инициализация модели машинного обучения")


        operation("Запрос transform", MdlCommand.TRANSFORMML) {
            stubs("Обработка стабов") {
                stubTransformSuccess("Имитация успешной обработки", corSettingsTransform)

                stubValidationBadTransform("Имитация ошибки валидации transform fields")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в mlTransformValidating") { mlTransformValidating = mlTransformMl.deepCopy() }
                worker("Копируем поля в mlResponseTrainModelValidating") { mlResponseTrainModelValidating = mlResponseTrainResult.deepCopy() }
                validateTransformHasContent("Валидация наличия текста в полях запроса")
                finishMlValidationTransform("Успешное завершение процедуры валидации")
            }
            training {
                title = "Тренировка модели машинного обучения"
                trainModelTransform("Тренировка модели перед сохранением в БД")
                worker("Копируем поля в mlValidated") { mlValidatedTransform = mlTrainModelTransformDone.deepCopy() }
            }
            chain {
                title = "Логика сохранения"
                repoPrepareDeleteTransform("Подготовка удаления модели из БД")
                repoDeleteTransform("Удаление модели из БД")
                repoPrepareCreateTransform("Подготовка объекта для сохранения")
                repoCreateTransform("Создание объявления в БД")
            }
            prepareTransformResult("Подготовка ответа")
        }

    }.build()
}
