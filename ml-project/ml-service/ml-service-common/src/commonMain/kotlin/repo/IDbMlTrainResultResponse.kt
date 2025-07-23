package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlTrainResult

sealed interface IDbMlTrainResultResponse: IDbTrainResultResponse<MdlMlTrainResult>

data class DbMlTrainResultResponseOk(
    val data: MdlMlTrainResult
): IDbMlTrainResultResponse

data class DbMlTrainResultResponseErr(
    val errors: List<MdlError> = emptyList()
): IDbMlTrainResultResponse {
    constructor(err: MdlError): this(listOf(err))
}

data class DbMlTrainResultResponseErrWithData(
    val data: MdlMlTrainResult,
    val errors: List<MdlError> = emptyList()
): IDbMlTrainResultResponse {
    constructor(ml: MdlMlTrainResult, err: MdlError): this(ml, listOf(err))
}
