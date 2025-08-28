package api.kotlinproject.common.stubs

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTitle
import api.kotlinproject.common.models.MdlMlTransform

object MdlMlTransformStubs {
    val ML_Transform: MdlMlTransform
        get() = MdlMlTransform(
            id = MdlMlId("1"),
            ticker = "NVDA",
            taskNumber = "123",
            dateStart = "1900-01-01",
            dateEnd = "2025-01-01",
            dateOffset = 1.toLong(),
            batchSize = 1,
            title = MdlMlTitle("forrest")
        )
}