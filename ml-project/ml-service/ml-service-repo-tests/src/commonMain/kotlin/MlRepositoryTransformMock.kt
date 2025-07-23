package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.*

class MlRepositoryTransformMock(
    private val invokeCreateMl: (DbMlTransformRequest) -> IDbMlTransformResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeReadMl: (DbMlIdTransformRequest) -> IDbMlTransformResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateMl: (DbMlTransformRequest) -> IDbMlTransformResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteMl: (DbMlIdTransformRequest) -> IDbMlTransformResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
): IRepoMlTransform {
    override suspend fun createMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse {
        return invokeCreateMl(rq)
    }

    override suspend fun readMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse {
        return invokeReadMl(rq)
    }

    override suspend fun updateMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse {
        return invokeUpdateMl(rq)
    }

    override suspend fun deleteMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse {
        return invokeDeleteMl(rq)
    }

    companion object {
        val DEFAULT_ML_SUCCESS_EMPTY_MOCK = DbMlTransformResponseOk(MdlMlTransform())
        val DEFAULT_MLS_SUCCESS_EMPTY_MOCK = DbMlsTransformResponseOk(emptyList())
    }
}
