package api.kotlinproject.stubs

import api.kotlinproject.common.models.MkplMl
import api.kotlinproject.stubs.MkplMlStubBolts.ML_DEMAND_BOLT1
import api.kotlinproject.stubs.MkplMlStubBolts.ML_SUPPLY_BOLT1

object MkplMlStub {
    fun get(): MkplMl = ML_DEMAND_BOLT1.copy()

    fun prepareResult(block: MkplMl.() -> Unit): MkplMl = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        mkplMlDemand("stub", "Модель STUB"),
        mkplMlDemand("stub", "Модель STUB")
    )

    fun prepareOffersList(title: String, description: String) = listOf(
        mkplMlSupply("stub", "Модель STUB"),
        mkplMlSupply("stub", "Модель STUB")
    )

    private fun mkplMlDemand(title: String, description: String) =
        mkplMl(ML_DEMAND_BOLT1, title = title, description = description)

    private fun mkplMlSupply(title: String, description: String) =
        mkplMl(ML_SUPPLY_BOLT1, title = title, description = description)

    private fun mkplMl(base: MkplMl, title: String, description: String) = base.copy(
        title = title,
        description = description,
    )

}
