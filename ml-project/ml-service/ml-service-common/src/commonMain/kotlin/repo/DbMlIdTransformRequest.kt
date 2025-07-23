package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTransform

data class DbMlIdTransformRequest(
    val id: MdlMlId,
) {
    constructor(ml: MdlMlTransform): this(id = ml.id)
}
