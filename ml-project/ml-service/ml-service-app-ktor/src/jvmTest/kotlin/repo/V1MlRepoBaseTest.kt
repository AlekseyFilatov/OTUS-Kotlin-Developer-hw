package api.kotlinproject.app.ktor.repo

import api.kotlinproject.api.jackson.v1.models.*
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.app.ktor.moduleJvm
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.mappers.v1.toTransportCreate
import api.kotlinproject.mappers.v1.toTransportDelete
import api.kotlinproject.mappers.v1.toTransportRead
import api.kotlinproject.mappers.v1.toTransportUpdate
import api.kotlinproject.stubs.MdlMlStub
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@Ignore
abstract class V1MlRepoBaseTest {
    abstract val workMode: MlRequestDebugMode
    abstract val appSettingsCreate: MdlAppSettings
    abstract val appSettingsRead:   MdlAppSettings
    abstract val appSettingsUpdate: MdlAppSettings
    abstract val appSettingsDelete: MdlAppSettings
    abstract val appSettingsSearch: MdlAppSettings


    protected val uuidOld = "10000000-0000-0000-0000-000000000001"
    protected val uuidNew = "10000000-0000-0000-0000-000000000002"
    protected val uuidSup = "10000000-0000-0000-0000-000000000003"
    protected val initMl = MdlMlStub.prepareResult {
        description = "описание модели"
        id = MdlMlId(uuidOld)
    }
    protected val initMlSupply = MdlMlStub.prepareResult {
        id = MdlMlId(uuidSup)
    }


    @Test
    fun create() {
        val ml = initMl.toTransportCreate()
        v1TestApplication(
            conf = appSettingsCreate,
            func = "create",
            request = MlCreateRequest(
                ml = ml,
                debug = MlDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<MlCreateResponse>()
            assertEquals(200, response.status.value)
            //assertEquals(uuidNew, responseObj.ml?.id)
            assertEquals(ml.title, responseObj.ml?.title)
            assertEquals(ml.description, responseObj.ml?.description)
        }
    }

    @Test
    fun read() {
        val ml = initMl.toTransportRead()
        v1TestApplication(
            conf = appSettingsRead,
            func = "read",
            request = MlReadRequest(
                ml = ml,
                debug = MlDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<IResponse>() as MlReadResponse
            assertEquals(200, response.status.value)
            //assertEquals(uuidOld, responseObj.ml?.id)
        }
    }

    @Test
    fun update() {
        val ml = initMl.toTransportUpdate()
        v1TestApplication(
            conf = appSettingsUpdate,
            func = "update",
            request = MlUpdateRequest(
                ml = ml,
                debug = MlDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<MlUpdateResponse>()
            assertEquals(200, response.status.value, responseObj.errors?.joinToString ( separator = "; " ))
            //assertEquals(ml.id, responseObj.ml?.id)
            assertEquals(ml.title, responseObj.ml?.title, responseObj.errors?.joinToString ( separator = "; " ))

        }
    }
    @Test
    fun delete() {
        val ml = initMl.toTransportDelete()
        v1TestApplication(
            conf = appSettingsDelete,
            func = "delete",
            request = MlDeleteRequest(
                ml = ml,
                debug = MlDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<MlDeleteResponse>()
            assertEquals(200, response.status.value)
            //assertEquals(uuidOld, responseObj.ml?.id)
        }
    }
    @Test
    fun search() = v1TestApplication(
        conf = appSettingsSearch,
        func = "search",
        request = MlSearchRequest(
            mlFilter = MlSearchFilter(),
            debug = MlDebug(mode = workMode),
        ),
    ) { response ->
        val responseObj = response.body<MlSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.mls?.size)
        //assertEquals(uuidOld, responseObj.mls?.first()?.id)
    }


    private inline fun <reified T: IRequest> v1TestApplication(
        conf: MdlAppSettings,
        func: String,
        request: T,
        crossinline function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { moduleJvm(appSettings = conf) }
        val client = createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.post("/v1/ml/$func") {
            contentType(ContentType.Application.Json)
            header("X-Trace-Id", "12345")
            setBody(request)
        }
        function(response)
    }
}
