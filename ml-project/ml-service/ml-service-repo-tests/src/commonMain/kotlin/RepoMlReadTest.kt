package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.repo.DbMlIdRequest
import api.kotlinproject.common.repo.DbMlResponseOk
import api.kotlinproject.common.repo.IRepoMl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoMlReadTest {
    abstract val repo: IRepoMl
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readMl(DbMlIdRequest(readSucc.id))

        assertIs<DbMlResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

   /* @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readMl(DbMlIdRequest(notFoundId))

        assertIs<DbMlResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }*/

    companion object : BaseInitMls("delete") {
        override val initObjects: List<MdlMl> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = MdlMlId("ml-repo-read-notFound")

    }
}
