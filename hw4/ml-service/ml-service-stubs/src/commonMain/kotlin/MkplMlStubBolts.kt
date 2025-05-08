package api.kotlinproject.stubs

import api.kotlinproject.common.models.MkplMl

object MkplMlStubBolts {
    val ML_DEMAND_BOLT1: MkplMl
        get() = MkplMl(
            title = "forest",
            description = "Модель forest"
        )
    val ML_SUPPLY_BOLT1 = ML_DEMAND_BOLT1.copy(title = "rapids", description = "Модель rapids")
}
