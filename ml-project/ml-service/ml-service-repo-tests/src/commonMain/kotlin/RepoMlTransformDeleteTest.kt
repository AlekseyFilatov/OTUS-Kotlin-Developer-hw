package api.kotlinproject.backend.repo.tests


import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.DbMlIdTransformRequest
import api.kotlinproject.common.repo.IRepoMlTransform
import kotlin.test.Test
import kotlin.test.assertNotNull

abstract class RepoMlTransformDeleteTest {
    abstract val repo: IRepoMlTransform
    protected open val deleteSucc = initObjects[0]
    protected open val notFoundId = MdlMlId("ml-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteMlTransform(DbMlIdTransformRequest(deleteSucc.id))
        assertNotNull(result)
    //assertIs<DbMlTransformResponseOk>(result)
        //assertEquals(deleteSucc.id, result.data.id)
    }

    /*@Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readMlTransform(DbMlIdTransformRequest(notFoundId))

        assertIs<DbMlTransformResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }*/

    companion object : BaseInitTransformMls("delete") {
        override val initObjects: List<MdlMlTransform> = listOf(
            createInitTestModel("delete"),
        )
    }
}
