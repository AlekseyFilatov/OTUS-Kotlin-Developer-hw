package api.kotlinproject.common.trainmodel

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlTransform

sealed interface ITrainModelMlsTransformResponse: ITrainModelTransformResponse<List<MdlMlTransform>>

data class TrainModelMlsTransformResponseOk(
    val data: List<MdlMlTransform>
): ITrainModelMlsTransformResponse

@Suppress("unused")
data class TrainModelMlsTransformResponseErr(
    val errors: List<MdlError> = emptyList()
): ITrainModelMlsTransformResponse {
    constructor(err: MdlError): this(listOf(err))
}