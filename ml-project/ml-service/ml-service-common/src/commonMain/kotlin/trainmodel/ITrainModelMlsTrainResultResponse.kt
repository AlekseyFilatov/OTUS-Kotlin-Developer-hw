package api.kotlinproject.common.trainmodel

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlTrainResult

sealed interface ITrainModelMlsTrainResultResponse: ITrainModelTrainResultResponse<List<MdlMlTrainResult>>

data class TrainModelMlsTrainResultResponseOk(
    val data: List<MdlMlTrainResult>
): ITrainModelMlsTrainResultResponse

@Suppress("unused")
data class TrainModelMlsTrainResultResponseErr(
    val errors: List<MdlError> = emptyList()
): ITrainModelMlsTrainResultResponse {
    constructor(err: MdlError): this(listOf(err))
}