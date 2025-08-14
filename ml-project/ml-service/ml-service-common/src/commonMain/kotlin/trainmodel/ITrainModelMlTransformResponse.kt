package api.kotlinproject.common.trainmodel

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlTransform

sealed interface ITrainModelMlTransformResponse: ITrainModelTransformResponse<MdlMlTransform>

data class TrainModelMlTransformResponseOk(
    val data: MdlMlTransform
): ITrainModelMlTransformResponse

data class TrainModelMlTransformResponseErr(
    val errors: List<MdlError> = emptyList()
): ITrainModelMlTransformResponse {
    constructor(err: MdlError): this(listOf(err))
}

data class TrainModelMlTransformResponseErrWithData(
    val data: MdlMlTransform,
    val errors: List<MdlError> = emptyList()
): ITrainModelMlTransformResponse {
    constructor(ml: MdlMlTransform, err: MdlError): this(ml, listOf(err))
}
