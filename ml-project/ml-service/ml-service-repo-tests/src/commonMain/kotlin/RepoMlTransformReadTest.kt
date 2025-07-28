package api.kotlinproject.backend.repo.tests


import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.DbMlIdTransformRequest
import api.kotlinproject.common.repo.IRepoMlTransform
import kotlin.test.Test
import kotlin.test.assertNotNull


abstract class RepoMlTransformReadTest {
    abstract val repo: IRepoMlTransform
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readMlTransform(DbMlIdTransformRequest(readSucc.id))
        assertNotNull(result)
        //assertIs<DbMlTransformResponseOk>(result)
        //assertEquals(readSucc, result.data)
    }

    /*@Test
    fun readNotFound() = runRepoTest {
        val result = repo.readMlTransform(DbMlIdTransformRequest(notFoundId))

        assertIs<DbMlTransformResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }*/

    companion object : BaseInitTransformMls("delete") {
        override val initObjects: List<MdlMlTransform> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = MdlMlId("ml-repo-read-notFound")

    }
}
