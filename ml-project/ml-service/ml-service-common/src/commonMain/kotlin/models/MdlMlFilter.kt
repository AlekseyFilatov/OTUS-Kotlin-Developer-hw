package api.kotlinproject.common.models

data class MdlMlFilter(
    var searchString: String = "",
    var ownerId: MdlUserId = MdlUserId.NONE
)
