package api.kotlinproject.biz

import api.kotlinproject.biz.general.initStatus
import api.kotlinproject.biz.general.operation
import api.kotlinproject.biz.general.stubs
import api.kotlinproject.biz.stubs.*
import api.kotlinproject.biz.validation.*
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlMlTitle
import api.kotlinproject.cor.rootChain
import api.kotlinproject.cor.worker
import biz.stubs.stubAnalyticSuccess
import biz.stubs.stubTransformSuccess
import biz.stubs.stubValidationBadAnalytic
import biz.stubs.stubValidationBadTransform
import biz.validation.validateAnalyticFieldsNotEmpty
import biz.validation.validateAnalyticHasContent
import biz.validation.validateTransformHasContent

class MdlMlProcessor(
    private val corSettings: MdlCorSettings = MdlCorSettings.NONE
) {
    suspend fun exec(ctx: MdlContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<MdlContext> {
        initStatus("Инициализация статуса")

        operation("Создание модели", MdlCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в mlValidating") { mlValidating = mlRequest.deepCopy() }
                worker("Очистка заголовка") { mlValidating.title = MdlMlTitle(mlValidating.title?.trim()).asString() }
                worker("Очистка описания") { mlValidating.description = mlValidating.description.trim() }
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                validateTitleHasContent("Проверка символов")
                validateDescriptionNotEmpty("Проверка, что описание не пусто")
                validateDescriptionHasContent("Проверка символов")

                finishMlValidation("Завершение проверок")
            }
        }
        operation("Получить модель", MdlCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации title")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в mlValidating") { mlValidating = mlRequest.deepCopy() }
                worker("Очистка title") { mlValidating.title =
                    MdlMlTitle(mlValidating.title.toString().trim()).asString()
                }
                validateTitleNotEmpty("Проверка на непустой title")

                finishMlValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Изменить модель", MdlCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в mlValidating") { mlValidating = mlRequest.deepCopy() }
                worker("Очистка заголовка") { mlValidating.title = MdlMlTitle(mlValidating.title?.trim()).asString() }
                worker("Очистка описания") { mlValidating.description = mlValidating.description.trim() }
                validateTitleNotEmpty("Проверка на непустой заголовок")
                validateTitleHasContent("Проверка на наличие содержания в заголовке")
                validateDescriptionNotEmpty("Проверка на непустое описание")
                validateDescriptionHasContent("Проверка на наличие содержания в описании")

                finishMlValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Удалить модель", MdlCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации title")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в mlValidating") {
                    mlValidating = mlRequest.deepCopy()
                }
                worker("Очистка title") { mlValidating.title = MdlMlTitle(mlValidating.title?.trim()).asString() }

                validateTitleNotEmpty("Проверка на непустой title")
                validateTitleProperFormat("Проверка формата title")
                finishMlValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Поиск моделей", MdlCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации title")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в mlFilterValidating") { mlFilterValidating = mlFilterRequest.deepCopy() }
                validateSearchStringLength("Валидация длины строки поиска в фильтре")

                finishMlValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Запрос analytic", MdlCommand.ANALITYCML) {
            stubs("Обработка стабов") {
                stubAnalyticSuccess("Имитация успешной обработки", corSettings)

                stubValidationBadAnalytic("Имитация ошибки валидации analytic fields")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в mlAnalyticValidating") { mlAnalyticValidating = mlAnalyticMl.deepCopy() }
                worker("Копируем поля в mlResponseTrainModelValidating") { mlResponseTrainModelValidating = mlResponseTrainModel.deepCopy() }
                validateAnalyticHasContent("Валидация наличия текста в полях запроса")
                validateAnalyticFieldsNotEmpty("Валидация заполнения полей запроса")
                finishMlValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Запрос transform", MdlCommand.TRANSFORMML) {
            stubs("Обработка стабов") {
                stubTransformSuccess("Имитация успешной обработки", corSettings)

                stubValidationBadTransform("Имитация ошибки валидации transform fields")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в mlTransformValidating") { mlTransformValidating = mlTransformMl.deepCopy() }
                worker("Копируем поля в mlResponseTrainModelValidating") { mlResponseTrainModelValidating = mlResponseTrainModel.deepCopy() }
                validateTransformHasContent("Валидация наличия текста в полях запроса")
                finishMlValidation("Успешное завершение процедуры валидации")
            }
        }

    }.build()
}
