package api.kotlinproject.common.models

data class MkplMlFilter(
    var searchString: String = "",
    var ownerId: MkplUserId = MkplUserId.NONE
)
