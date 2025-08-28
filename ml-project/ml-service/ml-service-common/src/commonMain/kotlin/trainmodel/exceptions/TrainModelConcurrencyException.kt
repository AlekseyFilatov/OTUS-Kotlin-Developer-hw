package api.kotlinproject.common.trainmodel.exceptions

import api.kotlinproject.common.models.MdlMlId

class TrainModelConcurrencyException(id: MdlMlId): TrainModelMlException(
    id,
    "Error in RepoConcurrencyException"
)