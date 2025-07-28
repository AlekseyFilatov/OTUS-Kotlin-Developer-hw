package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.DbMlTrainResultRequest
import api.kotlinproject.common.repo.DbMlTrainResultResponseOk
import api.kotlinproject.common.repo.IRepoMlTrainResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoMlTrainResultUpdateTest {
    abstract val repo: IRepoMlTrainResult
    protected open val updateSucc = initObjects[0]
    protected val updateIdNotFound = MdlMlId("ml-repo-update-not-found")

    private val reqUpdateSucc =
        MdlMlTrainResult(
            id = updateSucc.id,
        )

    private val reqUpdateNotFound = MdlMlTrainResult(
        id = updateIdNotFound,
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateMlTrainResult(DbMlTrainResultRequest(reqUpdateSucc))
        assertIs<DbMlTrainResultResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
    }

    /*@Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateMlTrainResult(DbMlTrainResultRequest(reqUpdateNotFound))
        assertIs<DbMlTrainResultResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }*/

    companion object : BaseInitTrainResultMls("update") {
        override val initObjects: List<MdlMlTrainResult> = listOf(
            createInitTestModel("update"),
        )
    }
}
