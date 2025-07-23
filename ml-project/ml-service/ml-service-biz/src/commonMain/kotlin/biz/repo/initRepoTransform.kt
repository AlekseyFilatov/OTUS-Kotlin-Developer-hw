package api.kotlinproject.biz.repo

import api.kotlinproject.biz.exceptions.MdlMlDbNotConfiguredException
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorSystem
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.common.repo.IRepoMlTransform
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.initRepoTransform(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        mlRepoTransform = when {
            workMode == MdlWorkMode.TEST -> corSettingsTransform.repoTestTransform
            workMode == MdlWorkMode.STUB -> corSettingsTransform.repoStubTransform
            else -> corSettingsTransform.repoProdTransform
        }
        if (workMode != MdlWorkMode.STUB && mlRepoTransform == IRepoMlTransform.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = MdlMlDbNotConfiguredException(workMode)
            )
        )
    }
}
