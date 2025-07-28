package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.repo.DbMlRequest
import api.kotlinproject.common.repo.DbMlResponseOk
import api.kotlinproject.repo.common.IRepoMlInitializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals


abstract class RepoMlCreateTest {
    abstract val repo: IRepoMlInitializable
    protected open val uuidNew = MdlMlId("10000000-0000-0000-0000-000000000001")

    private val createObj = MdlMl(
        title = "create object",
        description = "create object description",
        //id = uuidNew
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createMl(DbMlRequest(createObj))
        val expected = createObj
        assertIs<DbMlResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.title, result.data.title)
        assertEquals(expected.description, result.data.description)
        assertNotEquals(MdlMlId.NONE, result.data.id)
    }

    companion object : BaseInitMls("create") {
        override val initObjects: List<MdlMl> = emptyList()
    }
}
