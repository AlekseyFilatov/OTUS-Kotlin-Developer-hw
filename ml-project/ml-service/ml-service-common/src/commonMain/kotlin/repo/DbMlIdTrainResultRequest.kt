package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult

data class DbMlIdTrainResultRequest(
    val id: MdlMlId,
) {
    constructor(ml: MdlMlTrainResult): this(id = ml.id)
}
