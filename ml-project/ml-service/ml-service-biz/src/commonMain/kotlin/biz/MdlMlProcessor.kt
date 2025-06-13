package api.kotlinproject.biz

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.stubs.MdlMlStub

@Suppress("unused", "RedundantSuspendModifier")
class MdlMlProcessor(val corSettings: MdlCorSettings) {

    suspend fun exec(ctx: MdlContext) {
        ctx.mlResponse = MdlMlStub.get()
        ctx.mlsResponse = MdlMlStub.prepareSearchList("ml search").toMutableList()
        ctx.state = MdlState.RUNNING
    }
}
