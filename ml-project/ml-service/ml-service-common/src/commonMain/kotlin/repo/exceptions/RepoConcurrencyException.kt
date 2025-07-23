package api.kotlinproject.common.repo.exceptions

import api.kotlinproject.common.models.MdlMlId

class RepoConcurrencyException(id: MdlMlId): RepoMlException(
    id,
    "Error in RepoConcurrencyException"
)