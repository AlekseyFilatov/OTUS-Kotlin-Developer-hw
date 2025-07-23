package api.kotlinproject.backend.repository.inmemory

import api.kotlinproject.common.repo.*
import api.kotlinproject.stubs.MdlMlTransformStub

class MlRepoTransformStub() : IRepoMlTransform {

    override suspend fun updateMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse {
        return DbMlTransformResponseOk(
            data = MdlMlTransformStub.get(),
        )
    }

    override suspend fun createMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse {
        return DbMlTransformResponseOk(
            data = MdlMlTransformStub.get(),
        )
    }

    override suspend fun readMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse {
        return DbMlTransformResponseOk(
            data = MdlMlTransformStub.get(),
        )
    }

    override suspend fun deleteMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse {
        return DbMlTransformResponseOk(
            data = MdlMlTransformStub.get(),
        )
    }
}
