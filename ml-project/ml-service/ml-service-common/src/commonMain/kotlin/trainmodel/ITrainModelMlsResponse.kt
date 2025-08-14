package api.kotlinproject.common.trainmodel



import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMl

sealed interface ITrainModelMlsResponse: ITrainModelResponse<List<MdlMl>>

data class TrainModelMlsResponseOk(
    val data: List<MdlMl>
): ITrainModelMlsResponse

@Suppress("unused")
data class TrainModelMlsResponseErr(
    val errors: List<MdlError> = emptyList()
): ITrainModelMlsResponse {
    constructor(err: MdlError): this(listOf(err))
}