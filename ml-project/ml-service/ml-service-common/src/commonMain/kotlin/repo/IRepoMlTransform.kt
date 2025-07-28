package api.kotlinproject.common.repo


interface IRepoMlTransform {
    suspend fun updateMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse
    suspend fun createMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse
    suspend fun readMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse
    suspend fun deleteMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse

    companion object {
        val NONE = object : IRepoMlTransform {
            override suspend fun updateMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun createMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse {
                throw NotImplementedError("Must not be used")
            }

        }
    }
}
