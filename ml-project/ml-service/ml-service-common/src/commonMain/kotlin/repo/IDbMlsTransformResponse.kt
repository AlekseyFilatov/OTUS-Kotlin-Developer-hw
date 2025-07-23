package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlTransform

sealed interface IDbMlsTransformResponse: IDbTransformResponse<List<MdlMlTransform>>

data class DbMlsTransformResponseOk(
    val data: List<MdlMlTransform>
): IDbMlsTransformResponse

@Suppress("unused")
data class DbMlsTransformResponseErr(
    val errors: List<MdlError> = emptyList()
): IDbMlsTransformResponse {
    constructor(err: MdlError): this(listOf(err))
}
