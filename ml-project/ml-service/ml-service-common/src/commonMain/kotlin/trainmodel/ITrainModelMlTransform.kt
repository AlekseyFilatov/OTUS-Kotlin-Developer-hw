package api.kotlinproject.common.trainmodel

interface ITrainModelMlTransform : AutoCloseable {
suspend fun usingmodelMl(rq: TrainModelMlTransformRequest): ITrainModelMlTransformResponse
    override fun close() {}
companion object {
    val NONE = object : ITrainModelMlTransform {
        override suspend fun usingmodelMl(rq: TrainModelMlTransformRequest): ITrainModelMlTransformResponse {
            throw NotImplementedError("usingmodelMl : TrainModelTransform must not be used")
            }
        }
    }
}