package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.*

class MlRepositoryTrainResultMock(
    private val invokeCreateMl: (DbMlTrainResultRequest) -> IDbMlTrainResultResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeReadMl: (DbMlIdTrainResultRequest) -> IDbMlTrainResultResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateMl: (DbMlTrainResultRequest) -> IDbMlTrainResultResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteMl: (DbMlIdTrainResultRequest) -> IDbMlTrainResultResponse = { DEFAULT_ML_SUCCESS_EMPTY_MOCK },
): IRepoMlTrainResult {
    override suspend fun createMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse {
        return invokeCreateMl(rq)
    }

    override suspend fun readMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse {
        return invokeReadMl(rq)
    }

    override suspend fun updateMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse {
        return invokeUpdateMl(rq)
    }

    override suspend fun deleteMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse {
        return invokeDeleteMl(rq)
    }

    companion object {
        val DEFAULT_ML_SUCCESS_EMPTY_MOCK = DbMlTrainResultResponseOk(MdlMlTrainResult())
        val DEFAULT_MLS_SUCCESS_EMPTY_MOCK = DbMlsTrainResultResponseOk(emptyList())
    }
}
