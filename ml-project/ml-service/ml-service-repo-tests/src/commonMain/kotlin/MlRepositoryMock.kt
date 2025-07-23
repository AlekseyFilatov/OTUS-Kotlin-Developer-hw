package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.repo.*
import api.kotlinproject.common.repo.DbMlIdRequest
import api.kotlinproject.common.repo.DbMlRequest

class MlRepositoryMock(
    private val invokeCreateMl: (DbMlRequest) -> IDbMlResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeReadMl: (DbMlIdRequest) -> IDbMlResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateMl: (DbMlRequest) -> IDbMlResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteMl: (DbMlIdRequest) -> IDbMlResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeSearchMl: (DbMlFilterRequest) -> IDbMlsResponse = { DEFAULT_MLS_SUCCESS_EMPTY_MOCK },
): IRepoMl {
    override suspend fun createMl(rq: DbMlRequest): IDbMlResponse {
        return invokeCreateMl(rq)
    }

    override suspend fun readMl(rq: DbMlIdRequest): IDbMlResponse {
        return invokeReadMl(rq)
    }

    override suspend fun updateMl(rq: DbMlRequest): IDbMlResponse {
        return invokeUpdateMl(rq)
    }

    override suspend fun deleteMl(rq: DbMlIdRequest): IDbMlResponse {
        return invokeDeleteMl(rq)
    }

    override suspend fun searchMl(rq: DbMlFilterRequest): IDbMlsResponse {
        return invokeSearchMl(rq)
    }

    companion object {
        val DEFAULT_ML_SUCCESS_EMPTY_MOCK = DbMlResponseOk(MdlMl())
        val DEFAULT_MLS_SUCCESS_EMPTY_MOCK = DbMlsResponseOk(emptyList())
    }
}
