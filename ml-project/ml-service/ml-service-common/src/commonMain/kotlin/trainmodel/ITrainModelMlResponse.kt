package api.kotlinproject.common.trainmodel

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMl

sealed interface ITrainModelMlResponse: ITrainModelResponse<MdlMl>

data class TrainModelMlResponseOk(
    val data: MdlMl
): ITrainModelMlResponse

data class TrainModelMlResponseErr(
    val errors: List<MdlError> = emptyList()
): ITrainModelMlResponse {
    constructor(err: MdlError): this(listOf(err))
}

data class TrainModelMlResponseErrWithData(
    val data: MdlMl,
    val errors: List<MdlError> = emptyList()
): ITrainModelMlResponse {
    constructor(ml: MdlMl, err: MdlError): this(ml, listOf(err))
}
