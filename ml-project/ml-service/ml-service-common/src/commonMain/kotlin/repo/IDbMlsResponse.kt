package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlError

sealed interface IDbMlsResponse: IDbResponse<List<MdlMl>>

data class DbMlsResponseOk(
    val data: List<MdlMl>
): IDbMlsResponse

@Suppress("unused")
data class DbMlsResponseErr(
    val errors: List<MdlError> = emptyList()
): IDbMlsResponse {
    constructor(err: MdlError): this(listOf(err))
}
