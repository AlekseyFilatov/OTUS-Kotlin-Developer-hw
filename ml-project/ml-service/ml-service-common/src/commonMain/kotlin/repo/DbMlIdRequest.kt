package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId

data class DbMlIdRequest(
    val id: MdlMlId,
) {
    constructor(ml: MdlMl): this(id = ml.id)
}
