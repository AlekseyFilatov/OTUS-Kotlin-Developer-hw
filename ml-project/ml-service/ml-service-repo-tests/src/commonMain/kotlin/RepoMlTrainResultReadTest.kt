package api.kotlinproject.backend.repo.tests


import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.DbMlIdTrainResultRequest
import api.kotlinproject.common.repo.IRepoMlTrainResult
import kotlin.test.Test
import kotlin.test.assertNotNull


abstract class RepoMlTrainResultReadTest {
    abstract val repo: IRepoMlTrainResult
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readMlTrainResult(DbMlIdTrainResultRequest(readSucc.id))
        assertNotNull(result)
        //assertIs<DbMlTrainResultResponseOk>(result)
        //assertEquals(readSucc, result.data)
    }

    /*@Test
    fun readNotFound() = runRepoTest {
        val result = repo.readMlTrainResult(DbMlIdTrainResultRequest(notFoundId))

        assertIs<DbMlTrainResultResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }*/

    companion object : BaseInitTrainResultMls("delete") {
        override val initObjects: List<MdlMlTrainResult> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = MdlMlId("ml-repo-read-notFound")

    }
}
