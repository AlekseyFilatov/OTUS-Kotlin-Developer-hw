package api.kotlinproject.biz

import api.kotlinproject.biz.general.initStatus
import api.kotlinproject.biz.general.operation
import api.kotlinproject.biz.general.stubs
import api.kotlinproject.biz.repo.*
import api.kotlinproject.biz.stubs.stubDbError
import api.kotlinproject.biz.stubs.stubNoCase
import api.kotlinproject.biz.validation.finishMlValidationAnalytic
import api.kotlinproject.biz.validation.validation
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.MdlTrainResultCorrSettings
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.cor.chain
import api.kotlinproject.cor.rootChain
import api.kotlinproject.cor.worker
import biz.stubs.stubAnalyticSuccess
import biz.stubs.stubValidationBadAnalytic
import biz.trainmodel.trainModel
import biz.validation.validateAnalyticFieldsNotEmpty
import biz.validation.validateAnalyticHasContent

class MdlMlAnalyticProcessor(
    private val corSettingsTrainResult: MdlTrainResultCorrSettings = MdlTrainResultCorrSettings.NONE
) {
    suspend fun exec(ctx: MdlContext) = businessChain.exec(ctx.also
    {
        it.corSettingsTrainResult = corSettingsTrainResult
    })
    private val businessChain = rootChain<MdlContext> {
        initStatus("Инициализация статуса")
        initRepoTrainResult("Инициализация репозитория")

        operation("Запрос analytic", MdlCommand.ANALITYCML) {
            stubs("Обработка стабов") {
                stubAnalyticSuccess("Имитация успешной обработки", corSettingsTrainResult)

                stubValidationBadAnalytic("Имитация ошибки валидации analytic fields")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в mlAnalyticValidating") { mlTrainResultValidating = mlTrainResultMl.deepCopy() }
                worker("Копируем поля в mlResponseTrainModelValidating") { mlResponseTrainModelValidating = mlTrainResultValidating.deepCopy() }
                validateAnalyticHasContent("Валидация наличия текста в полях запроса")
                validateAnalyticFieldsNotEmpty("Валидация заполнения полей запроса")
                finishMlValidationAnalytic("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика сохранения"
                trainModel("Тренировка модели перед сохранением в БД")
                repoPrepareDeleteTrainResult("Подготовка к удалению из БД")
                repoDeleteTrainResult("Удаление модели из БД")
                repoPrepareCreateTrainResult("Подготовка объекта для сохранения")
                repoCreateTrainResult("Создание объявления в БД")
            }
            prepareTrainResultResult("Подготовка ответа")
        }

    }.build()
}
