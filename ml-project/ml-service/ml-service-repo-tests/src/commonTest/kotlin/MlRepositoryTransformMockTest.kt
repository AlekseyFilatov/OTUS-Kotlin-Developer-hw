package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.DbMlIdTransformRequest
import api.kotlinproject.common.repo.DbMlTransformRequest
import api.kotlinproject.common.repo.DbMlTransformResponseOk
import api.kotlinproject.stubs.MdlMlTransformStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MlRepositoryTransformMockTest {
    private val repo = MlRepositoryTransformMock(
        invokeCreateMl = { DbMlTransformResponseOk(MdlMlTransformStub.prepareResult { id = MdlMlId("321") }) },
        invokeReadMl = { DbMlTransformResponseOk(MdlMlTransformStub.prepareResult { id = MdlMlId("321") }) },
        invokeUpdateMl = { DbMlTransformResponseOk(MdlMlTransformStub.prepareResult { id = MdlMlId("321") }) },
        invokeDeleteMl = { DbMlTransformResponseOk(MdlMlTransformStub.prepareResult { id = MdlMlId("321") }) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createMlTransform(DbMlTransformRequest(MdlMlTransform()))
        assertIs<DbMlTransformResponseOk>(result)
        assertEquals(MdlMlId("321"), result.data.id)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readMlTransform(DbMlIdTransformRequest(MdlMlTransform()))
        assertIs<DbMlTransformResponseOk>(result)
        assertEquals(MdlMlId("321"), result.data.id)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateMlTransform(DbMlTransformRequest(MdlMlTransform()))
        assertIs<DbMlTransformResponseOk>(result)
        assertEquals(MdlMlId("321"), result.data.id
        )
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteMlTransform(DbMlIdTransformRequest(MdlMlTransform()))
        assertIs<DbMlTransformResponseOk>(result)
        assertEquals(MdlMlId("321"), result.data.id)
    }

}
