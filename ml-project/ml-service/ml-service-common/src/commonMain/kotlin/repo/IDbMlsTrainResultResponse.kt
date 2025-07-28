package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlTrainResult

sealed interface IDbMlsTrainResultResponse: IDbTrainResultResponse<List<MdlMlTrainResult>>

data class DbMlsTrainResultResponseOk(
    val data: List<MdlMlTrainResult>
): IDbMlsTrainResultResponse

@Suppress("unused")
data class DbMlsTrainResultResponseErr(
    val errors: List<MdlError> = emptyList()
): IDbMlsTrainResultResponse {
    constructor(err: MdlError): this(listOf(err))
}
