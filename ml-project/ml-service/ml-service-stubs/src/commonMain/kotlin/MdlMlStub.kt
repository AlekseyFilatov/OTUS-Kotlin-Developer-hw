package api.kotlinproject.stubs

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.stubs.MdlMlStubBolts.ML_DEMAND_BOLT1
import api.kotlinproject.stubs.MdlMlStubBolts.ML_SUPPLY_BOLT1

object MdlMlStub {
    fun get(): MdlMl = ML_DEMAND_BOLT1.copy()

    fun prepareResult(block: MdlMl.() -> Unit): MdlMl = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        mdlMlDemand("stub", "Модель STUB"),
        mdlMlDemand("stub", "Модель STUB")
    )

    fun prepareOffersList(title: String, description: String) = listOf(
        mdlMlSupply("stub", "Модель STUB"),
        mdlMlSupply("stub", "Модель STUB")
    )

    private fun mdlMlDemand(title: String, description: String) =
        mdlMl(ML_DEMAND_BOLT1, title = title, description = description)

    private fun mdlMlSupply(title: String, description: String) =
        mdlMl(ML_SUPPLY_BOLT1, title = title, description = description)

    private fun mdlMl(base: MdlMl, title: String, description: String) = base.copy(
        title = title,
        description = description,
    )

}
