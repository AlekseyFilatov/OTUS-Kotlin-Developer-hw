package api.kotlinproject.backend.repository.inmemory


import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.common.trainmodel.ITrainModelMlResponse
import api.kotlinproject.common.trainmodel.TrainModelMlRequest
import api.kotlinproject.common.trainmodel.TrainModelMlResponseOk
import api.kotlinproject.stubs.MdlMlStub

class MlTrainModelStub() : ITrainModelMl {
    override suspend fun usingmodelMl(rq: TrainModelMlRequest): ITrainModelMlResponse {
        return TrainModelMlResponseOk(
            data = MdlMlStub.get(),
        )
    }
}
