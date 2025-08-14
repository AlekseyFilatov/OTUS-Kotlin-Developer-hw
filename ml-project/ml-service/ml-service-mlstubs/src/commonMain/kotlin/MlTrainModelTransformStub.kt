package api.kotlinproject.backend.repository.inmemory

import api.kotlinproject.common.trainmodel.ITrainModelMlTransform
import api.kotlinproject.common.trainmodel.ITrainModelMlTransformResponse
import api.kotlinproject.common.trainmodel.TrainModelMlTransformRequest
import api.kotlinproject.common.trainmodel.TrainModelMlTransformResponseOk
import api.kotlinproject.stubs.MdlMlTransformStub

class MlTrainModelTransformStub() : ITrainModelMlTransform {
    override suspend fun usingmodelMl(rq: TrainModelMlTransformRequest): ITrainModelMlTransformResponse {
        return TrainModelMlTransformResponseOk(
            data = MdlMlTransformStub.get(),
        )
    }
}