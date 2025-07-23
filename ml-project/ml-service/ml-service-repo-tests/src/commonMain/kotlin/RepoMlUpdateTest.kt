package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTitle
import api.kotlinproject.common.repo.DbMlRequest
import api.kotlinproject.common.repo.DbMlResponseOk
import api.kotlinproject.common.repo.IRepoMl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoMlUpdateTest {
    abstract val repo: IRepoMl
    protected open val updateSucc = initObjects[0]
    protected val updateIdNotFound = MdlMlId("repo-not-found")

    private val reqUpdateSucc by lazy {
        MdlMl(
            id = updateSucc.id,
            title = "forrest",
            description = "update object description",
        )
    }
    private val reqUpdateNotFound  by lazy {
        MdlMl(
            id = updateIdNotFound,
            title = MdlMlTitle("forrest").asString(),
            description = "update object not found description",
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateMl(DbMlRequest(reqUpdateSucc))
        assertIs<DbMlResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.title, result.data.title)
        assertEquals(reqUpdateSucc.description, result.data.description)
    }

   /* @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateMl(DbMlRequest(reqUpdateNotFound))
        assertIs<DbMlResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }*/

    companion object : BaseInitMls("update") {
        override val initObjects: List<MdlMl> = listOf(
            createInitTestModel("update"),
        )
    }
}
