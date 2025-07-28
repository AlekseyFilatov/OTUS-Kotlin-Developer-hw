package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTransform

abstract class BaseInitTransformMls(private val op: String): IInitObjects<MdlMlTransform> {
    fun createInitTestModel(
        suf: String,
    ) = MdlMlTransform(
        id = MdlMlId("ml-repo-$op-$suf"),
        ticker = "NVDA",
        taskNumber = "123",
        dateStart = "1900-01-01",
        dateEnd = "2025-01-01",
        dateOffset = 1.toLong(),
        batchSize = 1
    )
}