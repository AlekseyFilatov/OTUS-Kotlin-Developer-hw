package app.kotlinproject.app.ktor

import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.app.ktor.module
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals


class ApplicationTest {
    @Test
    fun `root endpoint`() = testApplication {
        application { module(MdlAppSettings()) }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, world!", response.bodyAsText())
    }
}
