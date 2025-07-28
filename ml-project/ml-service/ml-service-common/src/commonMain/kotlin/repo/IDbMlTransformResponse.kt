package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlTransform

sealed interface IDbMlTransformResponse: IDbTransformResponse<MdlMlTransform>

data class DbMlTransformResponseOk(
    val data: MdlMlTransform
): IDbMlTransformResponse

data class DbMlTransformResponseErr(
    val errors: List<MdlError> = emptyList()
): IDbMlTransformResponse {
    constructor(err: MdlError): this(listOf(err))
}

data class DbMlTransformResponseErrWithData(
    val data: MdlMlTransform,
    val errors: List<MdlError> = emptyList()
): IDbMlTransformResponse {
    constructor(ml: MdlMlTransform, err: MdlError): this(ml, listOf(err))
}
