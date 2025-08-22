package api.kotlinproject.mlmodel.mlxgboost

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.common.trainmodel.TrainModelMlRequest
import api.kotlinproject.common.trainmodel.TrainModelMlResponseOk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals

abstract class TrainModelMlCreateTest {
    abstract val model: ITrainModelMl
    protected open val uuidNew = MdlMlId("10000000-0000-0000-0000-000000000001")

    private val createObj = MdlMl(
        title = "xgboost",
        description = "create model xgboost",
        id = uuidNew
    )

    @Test
    fun createSuccess() = runXGBoostDataFrameTest {
        val result = model.usingmodelMl(TrainModelMlRequest(createObj))
        val expected = createObj
        assertIs<TrainModelMlResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.title, result.data.title)
        assertEquals(true, result.data.description.contains("learn"))
        assertNotEquals(MdlMlId.Companion.NONE, result.data.id)
    }

    companion object : BaseTrainModelInitMls("create") {
        override val initObjects: List<MdlMl> = emptyList()
    }
}

class MlTrainModelCreateTest : TrainModelMlCreateTest() {
    override val model = XGBoostDataFrame()
}