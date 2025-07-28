package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlError

sealed interface IDbMlResponse: IDbResponse<MdlMl>

data class DbMlResponseOk(
    val data: MdlMl
): IDbMlResponse

data class DbMlResponseErr(
    val errors: List<MdlError> = emptyList()
): IDbMlResponse {
    constructor(err: MdlError): this(listOf(err))
}

data class DbMlResponseErrWithData(
    val data: MdlMl,
    val errors: List<MdlError> = emptyList()
): IDbMlResponse {
    constructor(ml: MdlMl, err: MdlError): this(ml, listOf(err))
}
