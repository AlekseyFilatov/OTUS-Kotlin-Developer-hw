package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.DbMlTransformRequest
import api.kotlinproject.common.repo.DbMlTransformResponseOk
import api.kotlinproject.repo.common.IRepoMlTransformInitializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals


abstract class RepoMlTransformCreateTest {
    abstract val repo: IRepoMlTransformInitializable
    protected open val uuidNew = MdlMlId("10000000-0000-0000-0000-000000000001")

    private val createObj = MdlMlTransform(
        id = uuidNew,
        ticker = "NVDA",
        taskNumber = "123",
        dateStart = "1900-01-01",
        dateEnd = "2025-01-01",
        dateOffset = 1.toLong(),
        batchSize = 1
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createMlTransform(DbMlTransformRequest(createObj))
        val expected = createObj
        assertIs<DbMlTransformResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.id, result.data.id)
        assertNotEquals(MdlMlId.NONE, result.data.id)
    }

    companion object : BaseInitTransformMls("create") {
        override val initObjects: List<MdlMlTransform> = emptyList()
    }
}
