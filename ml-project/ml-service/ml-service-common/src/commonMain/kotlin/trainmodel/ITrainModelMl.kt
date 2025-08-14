package api.kotlinproject.common.trainmodel

interface ITrainModelMl : AutoCloseable {
suspend fun usingmodelMl(rq: TrainModelMlRequest): ITrainModelMlResponse
    override fun close() {}
companion object {
    val NONE = object : ITrainModelMl {
        override suspend fun usingmodelMl(rq: TrainModelMlRequest): ITrainModelMlResponse {
            throw NotImplementedError("Must not be used")
            }
        }
    }
}