package api.kotlinproject.biz.exceptions

import api.kotlinproject.common.models.MdlWorkMode

class MdlMlDbNotConfiguredException(val workMode: MdlWorkMode): Exception(
    "Database is not configured properly for workmode $workMode"
)
