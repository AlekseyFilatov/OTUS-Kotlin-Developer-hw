package api.kotlinproject.common.trainmodel

interface ITrainModelMlTrainResult : AutoCloseable {
suspend fun usingmodelMl(rq: TrainModelMlTrainResultRequest): ITrainModelMlTrainResultResponse
    override fun close() {}
companion object {
    val NONE = object : ITrainModelMlTrainResult {
        override suspend fun usingmodelMl(rq: TrainModelMlTrainResultRequest): ITrainModelMlTrainResultResponse {
            throw NotImplementedError("Must not be used")
            }
        }
    }
}