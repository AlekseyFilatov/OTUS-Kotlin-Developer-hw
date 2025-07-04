package api.kotlinproject.common.stubs

import api.kotlinproject.common.models.MdlMlAnalytic
import api.kotlinproject.common.models.MdlMlModelParameters
import api.kotlinproject.common.models.MdlMlTaskNumber
import api.kotlinproject.common.models.MdlMlTicker

object MdlMlAnalyticStubs {
    val ML_Analytic: MdlMlAnalytic
        get() = MdlMlAnalytic(
            ticker = MdlMlTicker("NVDA"),
            taskNumber = MdlMlTaskNumber("123"),
            dateStart = "1900-01-01",
            dateEnd = "2025-01-01",
            evalPivotPoint = 0.toLong(),
            modelParameters = MdlMlModelParameters(
                learningRate = 0.toDouble(),
                maxDepth = 1,
                subSample = 1.toDouble(),
                gamma = 1,
                numRound = 500,
                treeMethod = "1",
                refreshLeaf = 1,
                processType = "default",
                updater = "1"
            ),
            dateOffset = 1.toLong(),
            batchSize = 1.toLong()
        )
}