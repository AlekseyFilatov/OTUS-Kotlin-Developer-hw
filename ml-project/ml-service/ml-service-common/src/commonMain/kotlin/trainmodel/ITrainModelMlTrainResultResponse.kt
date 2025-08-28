package api.kotlinproject.common.trainmodel

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlTrainResult

sealed interface ITrainModelMlTrainResultResponse: ITrainModelTrainResultResponse<MdlMlTrainResult>

data class TrainModelMlTrainResultResponseOk(
    val data: MdlMlTrainResult
): ITrainModelMlTrainResultResponse

data class TrainModelMlTrainResultResponseErr(
    val errors: List<MdlError> = emptyList()
): ITrainModelMlTrainResultResponse {
    constructor(err: MdlError): this(listOf(err))
}

data class TrainModelMlTrainResultResponseErrWithData(
    val data: MdlMlTrainResult,
    val errors: List<MdlError> = emptyList()
): ITrainModelMlTrainResultResponse {
    constructor(ml: MdlMlTrainResult, err: MdlError): this(ml, listOf(err))
}
