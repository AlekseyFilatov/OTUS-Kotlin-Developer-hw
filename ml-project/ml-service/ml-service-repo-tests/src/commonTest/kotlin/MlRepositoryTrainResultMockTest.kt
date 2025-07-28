package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.DbMlIdTrainResultRequest
import api.kotlinproject.common.repo.DbMlTrainResultRequest
import api.kotlinproject.common.repo.DbMlTrainResultResponseOk
import api.kotlinproject.stubs.MdlMlTrainResultStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MlRepositoryTrainResultMockTest {
    private val repo = MlRepositoryTrainResultMock(
        invokeCreateMl = { DbMlTrainResultResponseOk(MdlMlTrainResultStub.prepareResult { id = MdlMlId("321") }) },
        invokeReadMl = { DbMlTrainResultResponseOk(MdlMlTrainResultStub.prepareResult { id = MdlMlId("321") }) },
        invokeUpdateMl = { DbMlTrainResultResponseOk(MdlMlTrainResultStub.prepareResult { id = MdlMlId("321") }) },
        invokeDeleteMl = { DbMlTrainResultResponseOk(MdlMlTrainResultStub.prepareResult { id = MdlMlId("321") }) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createMlTrainResult(DbMlTrainResultRequest(MdlMlTrainResult()))
        assertIs<DbMlTrainResultResponseOk>(result)
        assertEquals(MdlMlId("321"), result.data.id)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readMlTrainResult(DbMlIdTrainResultRequest(MdlMlTrainResult()))
        assertIs<DbMlTrainResultResponseOk>(result)
        assertEquals(MdlMlId("321"), result.data.id)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateMlTrainResult(DbMlTrainResultRequest(MdlMlTrainResult()))
        assertIs<DbMlTrainResultResponseOk>(result)
        assertEquals(MdlMlId("321"), result.data.id
        )
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteMlTrainResult(DbMlIdTrainResultRequest(MdlMlTrainResult()))
        assertIs<DbMlTrainResultResponseOk>(result)
        assertEquals(MdlMlId("321"), result.data.id)
    }

}
