package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTitle

abstract class BaseInitMls(private val op: String): IInitObjects<MdlMl> {
    fun createInitTestModel(
        suf: String,
    ) = MdlMl(
        id = MdlMlId("ml-repo-$op-$suf"),
        title = MdlMlTitle("ml-repo-$op-$suf").asString(),
        description = "$suf stub description",
    )
}
