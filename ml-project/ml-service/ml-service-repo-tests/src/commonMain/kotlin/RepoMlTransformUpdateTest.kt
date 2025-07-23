package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.DbMlTransformRequest
import api.kotlinproject.common.repo.IRepoMlTransform
import kotlin.test.Test
import kotlin.test.assertNotNull


abstract class RepoMlTransformUpdateTest {
    abstract val repo: IRepoMlTransform
    protected open val updateSucc = initObjects[0]
    protected val updateIdNotFound = MdlMlId("ml-repo-update-not-found")

    private val reqUpdateSucc =
        MdlMlTransform(
            id = updateSucc.id,
        )

    private val reqUpdateNotFound = MdlMlTransform(
        id = updateIdNotFound,
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateMlTransform(DbMlTransformRequest(reqUpdateSucc))
        assertNotNull(result)
    // assertIs<DbMlTransformResponseOk>(result)
       // assertEquals(reqUpdateSucc.id, result.data.id)
    }

    /*@Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateMlTransform(DbMlTransformRequest(reqUpdateNotFound))
        assertIs<DbMlTransformResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }*/

    companion object : BaseInitTransformMls("update") {
        override val initObjects: List<MdlMlTransform> = listOf(
            createInitTestModel("update"),
        )
    }
}
