package api.kotlinproject.common.repo.exceptions

import api.kotlinproject.common.models.MdlMlId

open class RepoMlException(
    @Suppress("unused")
    val adId: MdlMlId,
    msg: String,
): RepoException(msg)
