package api.gorest.utils

import api.gorest.constants.*
import api.gorest.models.UserPayload
import com.github.javafaker.Faker
import io.qameta.allure.Step
import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import java.util.*

val faker = Faker(Locale("en-US"))

@Step("Создаём body active female для запроса -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyActiveFemale(): Map<String, Any?> {

    return mapOf(
        ID to getRandomInt(), GENDER to getGenderFemale(), NAME to getRandomName(),
        EMAIL to getRandomEmail(), STATUS to getActiveStatus()
    )
}

@Step("Создаём body inactive female для запроса -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyInactiveFemale(): Map<String, Any?> {

    return mapOf(
        ID to getRandomInt(), GENDER to getGenderFemale(), NAME to getRandomName(),
        EMAIL to getRandomEmail(), STATUS to getInactiveStatus()
    )
}

@Step("Создаём body inactive male для запроса -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyInactiveMale(): Map<String, Any?> {

    return mapOf(
        ID to getRandomInt(), GENDER to getGenderMale(), NAME to getRandomName(),
        EMAIL to getRandomEmail(), STATUS to getInactiveStatus()
    )
}

@Step("Создаём полное body active male для запроса -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyActiveMale(): Map<String, Any?> {
    return mapOf(
        ID to getRandomInt(), GENDER to getGenderMale(), NAME to getRandomName(),
        EMAIL to getRandomEmail(), STATUS to getActiveStatus()
    )
}


@Step("Ответ содержит ожидаемые заголовки (Transfer-Encoding, Connection, Server, Content-Type)")
fun ValidatableResponse.assertHeadersIsCorrect(): ValidatableResponse {
    return this.assertThat()
        .header("Connection", "keep-alive")
        .header("Transfer-Encoding", "chunked")
        .header("Server", "cloudflare")
        .header("Content-Type", "text/html; charset=UTF-8")
        .header("x-ratelimit-limit", "1000")
        .header("x-powered-by", "Express")
}

@Step("Ответ имеет HTTP-код {code}")
fun ValidatableResponse.assertStatusCode(code: Int): ValidatableResponse {
    return this.assertThat().statusCode(code)
}

@Step("Отправляем GET $BASE_URL")
fun getToBaseUrl(): Response {
    return RestAssured.given().get(BASE_URL)
}

@Step("Отправляем GET {path}")
fun sendGetReq(path: String): ValidatableResponse {
    return RestAssured.given().get(path).then()
}

@Step("Проверяем что тело ответа пустое")
fun ValidatableResponse.checkEmptyBody(): ValidatableResponse {
    return this.assertThat().body("isEmpty()", `is`(true))
}

@Step("Создаём active body для PUT запроса")
fun createActiveUserBodyPut(): Map<String, Any?> {
    return mapOf(
        NAME to getRandomName(), EMAIL to getRandomEmail(), STATUS to getActiveStatus()
    )
}

@Step("Создаём inactive body для PUT запроса c body")
fun createInactiveUserBodyPut(): Map<String, Any?> {
    return mapOf(
        NAME to getRandomName(), EMAIL to getRandomEmail(), STATUS to getInactiveStatus()
    )
}

@Step("Создаём сущность User в POST запросе c body -> {body}")
fun createUserPostRequest(body: Map<String, Any?>): ValidatableResponse {
    return RestAssured.given()
        .body(body)
        .post(USER_ENDPOINT)
        .then()
        .log().all()
}

@Step("Создаём сущность User в POST запросе c body -> {body}")
fun createUserPostRequest(body: String): ValidatableResponse {
    return RestAssured.given()
        .body(body)
        .post(USER_ENDPOINT)
        .then()
        .log().all()
}


@Step("Получаем полный URL с id для PUT запроса {userResp}")
fun getFullPathForPut(userResp: UserPayload): String {
    val id = userResp.id
    return USER_ENDPOINT + id
}

@Step("Отправляем PUT запрос с {updateBody} и {fullUSerPath} параметрами")
fun sendUserPutRequest(updateBody: Map<String, Any?>, fullUSerPath: String): ValidatableResponse {
    return RestAssured.given()
        .body(updateBody)
        .put(fullUSerPath)
        .then()
        .log().all()
}

@Step("Проверяем всё тело ответа после POST запроса создания сущности User -> {userPostResp}, {bodyPostReq}")
fun assertFullBodyAfterPost(userPostResp: UserPayload, bodyPostReq: Map<String, Any?>) {
    assertThat(userPostResp.name, equalTo(bodyPostReq[NAME]))
    assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
    assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
    assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
    assertThat(userPostResp.id, notNullValue())
    assertThat(userPostResp.gender, isOneOf(FEMALE, MALE)) //Ex FYI
}

@Step("Проверяем всё тело ответа после POST запроса создания сущности User -> {updateBodyResp}, {updateBody}, {userPostResp} ")
fun assertFullBodyAfterPut(updateBodyResp: UserPayload, updateBody: Map<String, Any?>, userPostResp: UserPayload) {
    assertThat(updateBodyResp.name, equalTo(updateBody[NAME]))
    assertThat(updateBodyResp.email, equalTo(updateBody[EMAIL]))
    assertThat(updateBodyResp.status, equalTo(updateBody[STATUS]))
    assertThat(updateBodyResp.gender, equalTo(userPostResp.gender))
    assertThat(updateBodyResp.id, equalTo(userPostResp.id))
}

@Step("Отправляем POST запрос на создание сущности User {body} и извлекаем запрос в класс ")
fun sendPostReqAndGetRespAndGetDTO(body: Map<String, Any?>): UserPayload {
    return createUserPostRequest(body).extract().body().`as`(UserPayload::class.java)
}

@Step("Отправляем DELETE запрос на удаление сущности User -> {fullUSerPath}")
fun sendDeleteReq(fullUSerPath: String): ValidatableResponse {
    return RestAssured.given().delete(fullUSerPath).then()
}






