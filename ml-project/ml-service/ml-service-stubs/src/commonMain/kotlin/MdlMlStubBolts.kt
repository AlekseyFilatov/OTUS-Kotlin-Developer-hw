package api.kotlinproject.stubs

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId

object MdlMlStubBolts {
    val ML_DEMAND_BOLT1: MdlMl
        get() = MdlMl(
            title = "forrest",
            description = "Модель forrest",
            id = MdlMlId("1")
        )
    val ML_SUPPLY_BOLT1 = ML_DEMAND_BOLT1.copy(title = "rapids", description = "Модель rapids", id = MdlMlId("1"))
}
