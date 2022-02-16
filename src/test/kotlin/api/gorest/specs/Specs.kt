package api.gorest.utils

import api.gorest.constants.BASE_PATH
import api.gorest.constants.BASE_URL
import api.gorest.constants.TOKEN
import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.RestAssured.*
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import io.restassured.specification.ResponseSpecification
import java.net.HttpURLConnection.*

val logConfig: LogConfig = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
val config: RestAssuredConfig = RestAssuredConfig.config().logConfig(logConfig)

fun requestSpec(): RequestSpecification? {
    val authScheme = oauth2(TOKEN)

    return RequestSpecBuilder()
        .setAuth(authScheme)
        .log(LogDetail.ALL)
        .addFilter(AllureRestAssured())
        .setBaseUri(BASE_URL)
        .setBasePath(BASE_PATH)
        .setContentType(ContentType.JSON)
        .setConfig(config)
        .build()
}

fun responseSpecOK200(): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(HTTP_OK)
        .expectContentType(ContentType.JSON)
        .expectHeaders(allHeadears201())
        .build()
}

fun responseSpecOK201(): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(HTTP_CREATED)
        .expectContentType(ContentType.JSON)
        .expectHeaders(allHeadears201())
        .build()
}

fun responseSpecOK204(): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(HTTP_NO_CONTENT)
        .expectHeaders(allHeadears204())
        .build()
}

fun responseSpecError400(): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(HTTP_BAD_REQUEST)
        .build()
}

fun responseSpecError401(): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(HTTP_UNAUTHORIZED)
        .build()
}

fun responseSpecError422(): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(422)
        .expectHeaders(allHeadears422())
        .build()
}

fun responseSpecError404(): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(HTTP_NOT_FOUND)
        .expectHeaders(allHeadears404())
        .expectContentType(ContentType.JSON)
        .build()
}

fun responseTextSpecError404(): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(HTTP_NOT_FOUND)
        .expectHeaders(allHeadearsText404())
        .expectContentType("text/html; charset=utf-8")
        .build()
}



fun responseSpecError406(): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(HTTP_NOT_ACCEPTABLE)
        .expectContentType("text/html; charset=UTF-8")
        .build()
}

fun responseSpec(status: Int): ResponseSpecification? {
    return ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .expectStatusCode(status)
        .expectContentType(ContentType.JSON)
        .build()
}

fun installSpecification(requestSpec: RequestSpecification?, responseSpec: ResponseSpecification?) {
    requestSpecification = requestSpec
    responseSpecification = responseSpec
}

fun installSpecification(requestSpec: RequestSpecification?) {
    requestSpecification = requestSpec
}

fun installSpecification(responseSpec: ResponseSpecification?) {
    responseSpecification = responseSpec
}

fun allHeadears404(): Map<String, Any> {
    return mapOf(
        "Server" to "nginx",
        "Content-Type" to "application/json; charset=utf-8",
        "Transfer-Encoding" to "chunked",
        "Connection" to "keep-alive",
        "X-Frame-Options" to "SAMEORIGIN",
        "X-XSS-Protection" to "0",
        "X-Content-Type-Options" to "nosniff",
        "X-Download-Options" to "noopen",
        "X-Permitted-Cross-Domain-Policies" to "none",
        "Cache-Control" to "no-cache"
    )
}

fun allHeadearsText404(): Map<String, Any>? {
    return mapOf(
        "Server" to "nginx",
        "Content-Type" to "text/html; charset=utf-8",
        "Transfer-Encoding" to "chunked",
        "Connection" to "keep-alive",
        "X-Frame-Options" to "SAMEORIGIN",
        "X-XSS-Protection" to "0",
        "X-Content-Type-Options" to "nosniff",
        "X-Download-Options" to "noopen",
        "X-Permitted-Cross-Domain-Policies" to "none",
        "Cache-Control" to "no-cache"
    )
}

fun allHeadears201(): Map<String, Any> {
    return mapOf(
        "Server" to "nginx",
        "Content-Type" to "application/json; charset=utf-8",
        "Transfer-Encoding" to "chunked",
        "Connection" to "keep-alive",
        "X-Frame-Options" to "SAMEORIGIN",
        "X-XSS-Protection" to "0",
        "X-Content-Type-Options" to "nosniff",
        "X-Download-Options" to "noopen",
        "X-Permitted-Cross-Domain-Policies" to "none",
        "Cache-Control" to "max-age=0, private, must-revalidate",
        "Referrer-Policy" to "strict-origin-when-cross-origin"
    )
}

fun allHeadears204(): Map<String, Any> {
    return mapOf(
        "Server" to "nginx",
        "Connection" to "keep-alive",
        "X-Frame-Options" to "SAMEORIGIN",
        "X-XSS-Protection" to "0",
        "X-Content-Type-Options" to "nosniff",
        "X-Download-Options" to "noopen",
        "X-Permitted-Cross-Domain-Policies" to "none",
        "Cache-Control" to "no-cache",
        "Referrer-Policy" to "strict-origin-when-cross-origin",
        "Vary" to "Origin"

    )

}

fun allHeadears422(): Map<String, Any> {
    return mapOf(
        "Server" to "nginx",
        "Connection" to "keep-alive",
        "X-Frame-Options" to "SAMEORIGIN",
        "X-XSS-Protection" to "0",
        "X-Content-Type-Options" to "nosniff",
        "X-Download-Options" to "noopen",
        "X-Permitted-Cross-Domain-Policies" to "none",
        "Cache-Control" to "no-cache",
        "Referrer-Policy" to "strict-origin-when-cross-origin",
        "Content-Type" to "application/json; charset=utf-8",
        "Vary" to "Origin"

    )
}
