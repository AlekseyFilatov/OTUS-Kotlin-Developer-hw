package api.kotlinproject.backend.repo.tests


import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.DbMlIdTrainResultRequest
import api.kotlinproject.common.repo.IRepoMlTrainResult
import kotlin.test.Test
import kotlin.test.assertNotNull

abstract class RepoMlTrainResultDeleteTest {
    abstract val repo: IRepoMlTrainResult
    protected open val deleteSucc = initObjects[0]
    protected open val notFoundId = MdlMlId("ml-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteMlTrainResult(DbMlIdTrainResultRequest(deleteSucc.id))
        assertNotNull(result)
       // assertIs<DbMlTrainResultResponseErr>(result)
       // assertEquals(deleteSucc.id, result.data.id)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readMlTrainResult(DbMlIdTrainResultRequest(notFoundId))
        assertNotNull(result)
       // assertIs<DbMlTrainResultResponseErr>(result)
        //val error = result.errors.find { it.code == "repo-not-found" }
        //assertNotNull(error)
    }

    companion object : BaseInitTrainResultMls("delete") {
        override val initObjects: List<MdlMlTrainResult> = listOf(
            createInitTestModel("delete"),
        )
    }
}
