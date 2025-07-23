package api.kotlinproject.backend.repository.inmemory
import api.kotlinproject.common.repo.*
import api.kotlinproject.stubs.MdlMlTrainResultStub

class MlRepoTrainResultStub() : IRepoMlTrainResult {

    override suspend fun updateMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse {
        return DbMlTrainResultResponseOk(
            data = MdlMlTrainResultStub.get(),
        )
    }

    override suspend fun createMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse {
        return DbMlTrainResultResponseOk(
            data = MdlMlTrainResultStub.get(),
        )
    }

    override suspend fun readMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse {
        return DbMlTrainResultResponseOk(
            data = MdlMlTrainResultStub.get(),
        )
    }

    override suspend fun deleteMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse {
        return DbMlTrainResultResponseOk(
            data = MdlMlTrainResultStub.get(),
        )
    }
}
