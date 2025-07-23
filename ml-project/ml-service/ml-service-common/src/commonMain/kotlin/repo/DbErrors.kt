package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.repo.exceptions.RepoConcurrencyException

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: MdlMlId) = DbMlResponseErr(
    MdlError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbMlResponseErr(
    MdlError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorRepoConcurrency(
    oldMl: MdlMl,
    exception: Exception = RepoConcurrencyException(
        id = oldMl.id,
    ),
) = DbMlResponseErrWithData(
    ml = oldMl,
    err = MdlError(
        code = "${api.kotlinproject.common.repo.ERROR_GROUP_REPO}-concurrency",
        group = api.kotlinproject.common.repo.ERROR_GROUP_REPO,
        field = "id",
        message = "The object with ID ${oldMl.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
        )
    )