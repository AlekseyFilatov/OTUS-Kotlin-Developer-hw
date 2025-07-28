package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.exceptions.RepoConcurrencyException

const val ERROR_GROUP_REPO_TRANSFORM = "repo-Transform"

fun errorTransformNotFound(id: MdlMlId) = DbMlTransformResponseErr(
    MdlError(
        code = "$ERROR_GROUP_REPO_TRANSFORM-not-found",
        group = ERROR_GROUP_REPO_TRANSFORM,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorTransformEmptyId = DbMlTransformResponseErr(
    MdlError(
        code = "$ERROR_GROUP_REPO_TRANSFORM-empty-id",
        group = ERROR_GROUP_REPO_TRANSFORM,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorTransformRepoConcurrency(
    oldMl: MdlMlTransform,
    exception: Exception = RepoConcurrencyException(
        id = oldMl.id,
    ),
) = DbMlTransformResponseErrWithData(
    ml = oldMl,
    err = MdlError(
        code = "${api.kotlinproject.common.repo.ERROR_GROUP_REPO}-concurrency",
        group = api.kotlinproject.common.repo.ERROR_GROUP_REPO,
        field = "id",
        message = "The object with ID ${oldMl.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    )
)