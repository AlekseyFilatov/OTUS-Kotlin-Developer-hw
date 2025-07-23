package api.kotlinproject.common.repo


interface IRepoMlTrainResult {
    suspend fun updateMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse
    suspend fun createMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse
    suspend fun readMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse
    suspend fun deleteMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse

    companion object {
        val NONE = object : IRepoMlTrainResult {
            override suspend fun updateMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun createMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse {
                throw NotImplementedError("Must not be used")
            }

        }
    }

}
