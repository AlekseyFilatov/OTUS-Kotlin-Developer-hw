package api.kotlinproject.biz

import api.kotlinproject.biz.general.initStatus
import api.kotlinproject.biz.general.operation
import api.kotlinproject.biz.general.stubs
import api.kotlinproject.biz.repo.*
import api.kotlinproject.biz.stubs.*
import api.kotlinproject.biz.trainmodel.initTrainModel
import api.kotlinproject.biz.trainmodel.trainModel
import api.kotlinproject.biz.trainmodel.training
import api.kotlinproject.biz.validation.*
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlMlTitle
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.chain
import api.kotlinproject.cor.rootChain
import api.kotlinproject.cor.worker

class MdlMlProcessor(
    private val corSettings: MdlCorSettings = MdlCorSettings.NONE,
) {
    suspend fun exec(ctx: MdlContext) = businessChain.exec(ctx.also
    {
        it.corSettings = corSettings
    })
    private val businessChain = rootChain<MdlContext> {
        initStatus("Инициализация статуса")
        initRepo("Инициализация репозитория")
        initTrainModel("Инициализация модели машинного обучения")

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
            training {
                title = "Применение модели машинного обучения"
                trainModel("Тренировка модели машинного обучения")
            }
            chain {
                title = "Логика сохранения"
                repoPrepareCreate("Подготовка объекта для сохранения")
                repoCreate("Создание объявления в БД")
            }
            prepareResult("Подготовка ответа")
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
                worker("Копируем поля в mlValidating") {
                    mlValidating = mlRequest.deepCopy()
                }
                finishMlValidation("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика чтения"
                repoRead("Чтение модели из БД")
                worker {
                    title = "Подготовка ответа для Read"
                    on { state == MdlState.RUNNING }
                    handle {mlRepoDone = mlRepoRead }
                }
            }
            prepareResult("Подготовка ответа")
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
                //validateDescriptionNotEmpty("Проверка на наличие содержания в описании")
                //validateDescriptionHasContent("Проверка на наличие содержания в описании")
                finishMlValidation("Успешное завершение процедуры валидации")
            }
            training {
                title = "Изменение готовой модели машинного обучения"
                trainModel("Изменение готовой модели")
                worker("Копируем поля в mlValidated") { mlValidated = mlTrainModelResultDone.deepCopy() }
            }
            chain {
                title = "Логика сохранения"
                repoRead("Чтение модели из БД")
                repoPrepareUpdate("Подготовка модели для обновления")
                repoUpdate("Обновление модели в БД")
            }
            prepareResult("Подготовка ответа")
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
            chain {
                title = "Логика удаления"
                repoRead("Чтение объявления из БД")
                repoPrepareDelete("Подготовка объекта для удаления")
                repoDelete("Удаление объявления из БД")
            }
            prepareResult("Подготовка ответа")
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
            repoSearch("Поиск моделей в БД по фильтру")
            prepareResult("Подготовка ответа")
        }

    }.build()
}
