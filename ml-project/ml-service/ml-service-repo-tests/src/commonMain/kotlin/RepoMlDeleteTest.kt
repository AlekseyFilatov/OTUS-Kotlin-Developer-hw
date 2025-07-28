package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.repo.*
import api.kotlinproject.common.repo.DbMlIdRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoMlDeleteTest {
    abstract val repo: IRepoMl
    protected open val deleteSucc = initObjects[0]
    protected open val notFoundId = MdlMlId("ml-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteMl(DbMlIdRequest(deleteSucc.id))
        assertIs<DbMlResponseOk>(result)
        assertEquals(deleteSucc.title, result.data.title)
        assertEquals(deleteSucc.description, result.data.description)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readMl(DbMlIdRequest(notFoundId))

        assertIs<DbMlResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    companion object : BaseInitMls("delete") {
        override val initObjects: List<MdlMl> = listOf(
            createInitTestModel("delete"),
        )
    }
}
