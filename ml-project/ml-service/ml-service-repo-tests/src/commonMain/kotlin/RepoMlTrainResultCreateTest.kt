package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.DbMlTrainResultRequest
import api.kotlinproject.common.repo.DbMlTrainResultResponseOk
import api.kotlinproject.repo.common.IRepoMlTrainResultInitializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals


abstract class RepoMlTrainResultCreateTest {
    abstract val repo: IRepoMlTrainResultInitializable
    protected open val uuidNew = MdlMlId("10000000-0000-0000-0000-000000000001")

    private val createObj = MdlMlTrainResult(
        id = uuidNew,
        dateTime  = "",
        close = 0.0,
        labelDatetime = "",
        realResult = 0.0,
        prediction = 0.0,
        error = 0.0
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createMlTrainResult(DbMlTrainResultRequest(createObj))
        val expected = createObj
        assertIs<DbMlTrainResultResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.id, result.data.id)
        assertNotEquals(MdlMlId.NONE, result.data.id)
    }

    companion object : BaseInitTrainResultMls("create") {
        override val initObjects: List<MdlMlTrainResult> = emptyList()
    }
}
