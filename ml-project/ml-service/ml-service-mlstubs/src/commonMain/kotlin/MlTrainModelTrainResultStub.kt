package api.kotlinproject.backend.repository.inmemory

import api.kotlinproject.common.trainmodel.ITrainModelMlTrainResult
import api.kotlinproject.common.trainmodel.ITrainModelMlTrainResultResponse
import api.kotlinproject.common.trainmodel.TrainModelMlTrainResultRequest
import api.kotlinproject.common.trainmodel.TrainModelMlTrainResultResponseOk
import api.kotlinproject.stubs.MdlMlTrainResultStub

class MlTrainModelTrainResultStub() : ITrainModelMlTrainResult {
    override suspend fun usingmodelMl(rq: TrainModelMlTrainResultRequest): ITrainModelMlTrainResultResponse {
        return TrainModelMlTrainResultResponseOk(
            data = MdlMlTrainResultStub.get(),
        )
    }
}