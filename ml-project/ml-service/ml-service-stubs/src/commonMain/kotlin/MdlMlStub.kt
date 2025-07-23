package api.kotlinproject.stubs

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.stubs.MdlMlStubBolts.ML_DEMAND_BOLT1
import api.kotlinproject.stubs.MdlMlStubBolts.ML_SUPPLY_BOLT1

object MdlMlStub {
    fun get(): MdlMl = ML_DEMAND_BOLT1.copy()

    fun prepareResult(block: MdlMl.() -> Unit): MdlMl = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        mdlMlDemand("stub", "Модель STUB", id = "1"),
        mdlMlDemand("stub", "Модель STUB", id = "1")
    )

    fun prepareOffersList(title: String, description: String) = listOf(
        mdlMlSupply("stub", "Модель STUB", id = "1"),
        mdlMlSupply("stub", "Модель STUB", id = "1")
    )

    private fun mdlMlDemand(title: String, description: String, id: String) =
        mdlMl(ML_DEMAND_BOLT1, title = title, description = description, id = id)

    private fun mdlMlSupply(title: String, description: String, id: String ) =
        mdlMl(ML_SUPPLY_BOLT1, title = title, description = description, id = id)

    private fun mdlMl(base: MdlMl, title: String, description: String, id: String) = base.copy(
        title = title,
        description = description,
        id = MdlMlId(id)
    )

}
