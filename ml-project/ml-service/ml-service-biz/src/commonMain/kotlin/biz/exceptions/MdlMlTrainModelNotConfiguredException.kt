package biz.exceptions

import api.kotlinproject.common.models.MdlTitle

class MdlMlTrainModelNotConfiguredException(val titleMdlMl: MdlTitle): Exception(
    "Model is not configured properly for mlmodel $titleMdlMl"
)