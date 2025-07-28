package api.kotlinproject.common.repo

interface IRepoMl {
    suspend fun createMl(rq: DbMlRequest): IDbMlResponse
    suspend fun readMl(rq: DbMlIdRequest): IDbMlResponse
    suspend fun updateMl(rq: DbMlRequest): IDbMlResponse
    suspend fun deleteMl(rq: DbMlIdRequest): IDbMlResponse
    suspend fun searchMl(rq: DbMlFilterRequest): IDbMlsResponse
    companion object {
        val NONE = object : IRepoMl {
            override suspend fun createMl(rq: DbMlRequest): IDbMlResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readMl(rq: DbMlIdRequest): IDbMlResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateMl(rq: DbMlRequest): IDbMlResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteMl(rq: DbMlIdRequest): IDbMlResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchMl(rq: DbMlFilterRequest): IDbMlsResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}
