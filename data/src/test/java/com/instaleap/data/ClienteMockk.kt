package com.instaleap.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun clientMockkError() =
    clienteMockk(
        MockEngine.invoke {
            responseMockk(
                status = HttpStatusCode.BadRequest,
            )
        },
    )

fun clienteMockk(mockEngine: MockEngine): HttpClient =
    HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                },
            )
        }
    }

fun MockRequestHandleScope.responseMockk(
    content: String = "",
    status: io.ktor.http.HttpStatusCode = io.ktor.http.HttpStatusCode.OK,
) = respond(
    status = status,
    content = content,
    headers =
        headersOf(
            HttpHeaders.ContentType,
            ContentType.Application.Json.toString(),
        ),
)
