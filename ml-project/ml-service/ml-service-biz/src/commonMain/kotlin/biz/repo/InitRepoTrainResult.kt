package api.kotlinproject.biz.repo

import api.kotlinproject.biz.exceptions.MdlMlDbNotConfiguredException
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorSystem
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.common.repo.IRepoMlTrainResult
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.initRepoTrainResult(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        mlRepoTrainResult = when {
            workMode == MdlWorkMode.TEST -> corSettingsTrainResult.repoTestTrainResult
            workMode == MdlWorkMode.STUB -> corSettingsTrainResult.repoStubTrainResult
            else -> corSettingsTrainResult.repoProdTrainResult
        }
        if (workMode != MdlWorkMode.STUB && mlRepoTrainResult == IRepoMlTrainResult.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = MdlMlDbNotConfiguredException(workMode)
            )
        )
    }
}
