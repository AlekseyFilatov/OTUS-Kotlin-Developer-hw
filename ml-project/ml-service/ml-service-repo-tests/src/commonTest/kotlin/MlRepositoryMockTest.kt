package api.kotlinproject.backend.repo.tests

import kotlinx.coroutines.test.runTest
import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.repo.*
import api.kotlinproject.stubs.MdlMlStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MlRepositoryMockTest {
    private val repo = MlRepositoryMock(
        invokeCreateMl = { DbMlResponseOk(MdlMlStub.prepareResult { title = "create" }) },
        invokeReadMl = { DbMlResponseOk(MdlMlStub.prepareResult { title = "read" }) },
        invokeUpdateMl = { DbMlResponseOk(MdlMlStub.prepareResult { title = "update" }) },
        invokeDeleteMl = { DbMlResponseOk(MdlMlStub.prepareResult { title = "delete" }) },
        invokeSearchMl = { DbMlsResponseOk(listOf(MdlMlStub.prepareResult { title = "search" })) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createMl(DbMlRequest(MdlMl()))
        assertIs<DbMlResponseOk>(result)
        assertEquals("create", result.data.title)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readMl(DbMlIdRequest(MdlMl()))
        assertIs<DbMlResponseOk>(result)
        assertEquals("read", result.data.title)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateMl(DbMlRequest(MdlMl()))
        assertIs<DbMlResponseOk>(result)
        assertEquals("update", result.data.title)
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteMl(DbMlIdRequest(MdlMl()))
        assertIs<DbMlResponseOk>(result)
        assertEquals("delete", result.data.title)
    }

    @Test
    fun mockSearch() = runTest {
        val result = repo.searchMl(DbMlFilterRequest())
        assertIs<DbMlsResponseOk>(result)
        assertEquals("search", result.data.first().title)
    }

}
