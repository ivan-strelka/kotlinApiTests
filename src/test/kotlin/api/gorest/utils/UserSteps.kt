package api.gorest.utils

import api.gorest.constants.*
import api.gorest.models.UserPayload
import com.github.javafaker.Faker
import io.qameta.allure.Step
import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import java.util.*

val faker = Faker(Locale("en-US"))

@Step("Создаём body active female для запроса -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyActiveFemale(): Map<String, Any?> {

    return mapOf(
        ID to createRandomInt(), GENDER to getGenderFemale(), NAME to createRandomName(),
        EMAIL to createRandomEmail(), STATUS to getActiveStatus()
    )
}

@Step("Создаём body inactive female для запроса -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyInactiveFemale(): Map<String, Any?> {

    return mapOf(
        ID to createRandomInt(), GENDER to getGenderFemale(), NAME to createRandomName(),
        EMAIL to createRandomEmail(), STATUS to getInactiveStatus()
    )
}

@Step("Создаём body inactive male для запроса -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyInactiveMale(): Map<String, Any?> {

    return mapOf(
        ID to createRandomInt(), GENDER to getGenderMale(), NAME to createRandomName(),
        EMAIL to createRandomEmail(), STATUS to getInactiveStatus()
    )
}

@Step("Создаём body active male для запроса -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyActiveMale(): Map<String, Any?> {

    return mapOf(
        ID to createRandomInt(), GENDER to getGenderMale(), NAME to createRandomName(),
        EMAIL to createRandomEmail(), STATUS to getActiveStatus()
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
    return RestAssured.given().get("$path").then()
}

@Step("Проверяем что тело ответа пустое")
fun ValidatableResponse.checkEmptyBody(): ValidatableResponse {
    return this.assertThat().body("isEmpty()", Matchers.`is`(true))
}

@Step("Создаём active body для PUT запроса")
fun createActiveUserBodyPut(): Map<String, Any?> {
    val name = createRandomName()
    val email = createRandomEmail()
    val status = getActiveStatus()
    return mapOf(
        NAME to name, EMAIL to email, STATUS to status
    )
}

@Step("Создаём inactive body для PUT запроса c body")
fun createInactiveUserBodyPut(): Map<String, Any?> {
    val name = createRandomName()
    val email = createRandomEmail()
    val status = getInactiveStatus()
    return mapOf(
        NAME to name, EMAIL to email, STATUS to status
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

@Step("Получаем полный URL с id для PUT запроса {userResp}")
fun getFullPathForPut(userResp: UserPayload): String {
    val id = userResp.id
    return "$USER_ENDPOINT" + id
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
    MatcherAssert.assertThat(userPostResp.name, Matchers.equalTo(bodyPostReq[NAME]))
    MatcherAssert.assertThat(userPostResp.email, Matchers.equalTo(bodyPostReq[EMAIL]))
    MatcherAssert.assertThat(userPostResp.gender, Matchers.equalTo(bodyPostReq[GENDER]))
    MatcherAssert.assertThat(userPostResp.status, Matchers.equalTo(bodyPostReq[STATUS]))
    MatcherAssert.assertThat(userPostResp.id, Matchers.notNullValue())
    MatcherAssert.assertThat(userPostResp.gender, Matchers.isOneOf(FEMALE, MALE)) //Ex FYI
}

@Step("Проверяем всё тело ответа после POST запроса создания сущности User -> {updateBodyResp}, {updateBody}, {userPostResp} ")
fun assertFullBodyAfterPut(updateBodyResp: UserPayload, updateBody: Map<String, Any?>, userPostResp: UserPayload) {
    MatcherAssert.assertThat(updateBodyResp.name, Matchers.equalTo(updateBody[NAME]))
    MatcherAssert.assertThat(updateBodyResp.email, Matchers.equalTo(updateBody[EMAIL]))
    MatcherAssert.assertThat(updateBodyResp.status, Matchers.equalTo(updateBody[STATUS]))
    MatcherAssert.assertThat(updateBodyResp.gender, Matchers.equalTo(userPostResp.gender))
    MatcherAssert.assertThat(updateBodyResp.id, Matchers.equalTo(userPostResp.id))
}

@Step("Отправляем POST запрос на создание сущности User {body} и извлекаем запрос в класс ")
fun sendPostReqAndGetRespToDTO(body: Map<String, Any?>): UserPayload {
    return createUserPostRequest(body).extract().body().`as`(UserPayload::class.java)
}

@Step("Отправляем DELETE запрос на удаление сущности User -> {fullUSerPath}")
fun sendDeleteReq(fullUSerPath: String): ValidatableResponse {
    return RestAssured.given().delete(fullUSerPath).then()
}






