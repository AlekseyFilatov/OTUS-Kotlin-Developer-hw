package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlUserId


data class DbMlFilterRequest(
    val titleFilter: String = "",
    val ownerId: MdlUserId = MdlUserId.NONE,
    )
