package api.kotlinproject.biz.repo

import api.kotlinproject.biz.exceptions.MdlMlDbNotConfiguredException
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorSystem
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.common.repo.IRepoMl
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        mlRepo = when {
            workMode == MdlWorkMode.TEST -> corSettings.repoTest
            workMode == MdlWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != MdlWorkMode.STUB && mlRepo == IRepoMl.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = MdlMlDbNotConfiguredException(workMode)
            )
        )
    }
}
