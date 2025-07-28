package api.kotlinproject.common.repo

import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.exceptions.RepoConcurrencyException

const val ERROR_GROUP_REPO_TRAIN_RESULT = "repo-TrainResult"

fun errorTrainResultNotFound(id: MdlMlId) = DbMlTrainResultResponseErr(
    MdlError(
        code = "$ERROR_GROUP_REPO_TRAIN_RESULT-not-found",
        group = ERROR_GROUP_REPO_TRAIN_RESULT,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorTrainResultEmptyId = DbMlTrainResultResponseErr(
    MdlError(
        code = "$ERROR_GROUP_REPO_TRAIN_RESULT-empty-id",
        group = ERROR_GROUP_REPO_TRAIN_RESULT,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorTrainResultRepoConcurrency(
    oldMl: MdlMlTrainResult,
    exception: Exception = RepoConcurrencyException(
        id = oldMl.id,
    ),
) = DbMlTrainResultResponseErrWithData(
    ml = oldMl,
    err = MdlError(
        code = "${api.kotlinproject.common.repo.ERROR_GROUP_REPO}-concurrency",
        group = api.kotlinproject.common.repo.ERROR_GROUP_REPO,
        field = "id",
        message = "The object with ID ${oldMl.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    )
)