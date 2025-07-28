package api.kotlinproject.backend.repository.inmemory


import api.kotlinproject.common.repo.*
import api.kotlinproject.common.repo.DbMlIdRequest
import api.kotlinproject.common.repo.DbMlRequest
import api.kotlinproject.stubs.MdlMlStub

class MlRepoStub() : IRepoMl {
    override suspend fun createMl(rq: DbMlRequest): IDbMlResponse {
        return DbMlResponseOk(
            data = MdlMlStub.get(),
        )
    }

    override suspend fun readMl(rq: DbMlIdRequest): IDbMlResponse {
        return DbMlResponseOk(
            data = MdlMlStub.get(),
        )
    }

    override suspend fun updateMl(rq: DbMlRequest): IDbMlResponse {
        return DbMlResponseOk(
            data = MdlMlStub.get(),
        )
    }

    override suspend fun deleteMl(rq: DbMlIdRequest): IDbMlResponse {
        return DbMlResponseOk(
            data = MdlMlStub.get(),
        )
    }

    override suspend fun searchMl(rq: DbMlFilterRequest): IDbMlsResponse {
        return DbMlsResponseOk(
            data = MdlMlStub.prepareSearchList(filter = ""),
        )
    }
}
