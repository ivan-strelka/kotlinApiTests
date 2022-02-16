package api.gorest.utils

import api.gorest.constants.*
import api.gorest.models.UserPayload
import com.github.javafaker.Faker
import io.qameta.allure.Step
import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import java.util.*

val faker = Faker(Locale("en-US"))

@Step("Create a body active female for the request -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyActiveFemale(): Map<String, Any?> {

    return mapOf(
        ID to getRandomInt(), GENDER to getGenderFemale(), NAME to getRandomName(),
        EMAIL to getRandomEmail(), STATUS to getActiveStatus()
    )
}

@Step("Create a body inactive female for the request -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyInactiveFemale(): Map<String, Any?> {

    return mapOf(
        ID to getRandomInt(), GENDER to getGenderFemale(), NAME to getRandomName(),
        EMAIL to getRandomEmail(), STATUS to getInactiveStatus()
    )
}

@Step("Create body inactive male for request -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyInactiveMale(): Map<String, Any?> {

    return mapOf(
        ID to getRandomInt(), GENDER to getGenderMale(), NAME to getRandomName(),
        EMAIL to getRandomEmail(), STATUS to getInactiveStatus()
    )
}

@Step("Create full body active male for request -> $BASE_URL$BASE_PATH$USER_ENDPOINT")
fun createUserBodyActiveMale(): Map<String, Any?> {
    return mapOf(
        ID to getRandomInt(), GENDER to getGenderMale(), NAME to getRandomName(),
        EMAIL to getRandomEmail(), STATUS to getActiveStatus()
    )
}

@Step("Response contains expected headers -> Transfer-Encoding, Connection, Server, Content-Type")
fun ValidatableResponse.assertHeadersIsCorrect(): ValidatableResponse {
    return this.assertThat()
        .header("Connection", "keep-alive")
        .header("Transfer-Encoding", "chunked")
        .header("Server", "cloudflare")
        .header("Content-Type", "text/html; charset=UTF-8")
        .header("x-ratelimit-limit", "1000")
        .header("x-powered-by", "Express")
}

@Step("The response has an HTTP code {code}")
fun ValidatableResponse.assertStatusCode(code: Int): ValidatableResponse {
    return this.assertThat().statusCode(code)
}

@Step("Send GET request -> $BASE_URL")
fun getToBaseUrl(): Response {
    return RestAssured.given().get(BASE_URL)
}

@Step("Send GET request -> {path}")
fun sendGetReq(path: String): ValidatableResponse {
    return RestAssured.given().get(path).then()
}

@Step("Checking the response body is empty")
fun ValidatableResponse.checkEmptyBody(): ValidatableResponse {
    return this.assertThat().body("isEmpty()", `is`(true))
}

@Step("Create an active body for a PUT request")
fun createActiveUserBodyPut(): Map<String, Any?> {
    return mapOf(
        NAME to getRandomName(), EMAIL to getRandomEmail(), STATUS to getActiveStatus()
    )
}

@Step("Create inactive body for PUT request from body")
fun createInactiveUserBodyPut(): Map<String, Any?> {
    return mapOf(
        NAME to getRandomName(), EMAIL to getRandomEmail(), STATUS to getInactiveStatus()
    )
}

@Step("Create the User entity in the POST request with body -> {body}")
fun createUserPostRequest(body: Map<String, Any?>): ValidatableResponse {
    return RestAssured.given()
        .body(body)
        .post(USER_ENDPOINT)
        .then()
        .log().all()
}

@Step("Create the User entity in the POST request with body -> {body}")
fun createUserPostRequest(body: String): ValidatableResponse {
    return RestAssured.given()
        .body(body)
        .post(USER_ENDPOINT)
        .then()
        .log().all()
}


@Step("Get full URL with id for PUT request -> {userResp}")
fun getFullPathForPut(userResp: UserPayload): String {
    val id = userResp.id
    return USER_ENDPOINT + id
}

@Step("Sending a PUT request with {updateBody} and {fullUSerPath} parameters")
fun sendUserPutRequest(updateBody: Map<String, Any?>, fullUSerPath: String): ValidatableResponse {
    return RestAssured.given()
        .body(updateBody)
        .put(fullUSerPath)
        .then()
        .log().all()
}

@Step("Checking the entire response body after the POST request to create the User entity -> {userPostResp}, {bodyPostReq}")
fun assertFullBodyAfterPost(userPostResp: UserPayload, bodyPostReq: Map<String, Any?>) {
    assertThat(userPostResp.name, equalTo(bodyPostReq[NAME]))
    assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
    assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
    assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
    assertThat(userPostResp.id, notNullValue())
    assertThat(userPostResp.gender, isOneOf(FEMALE, MALE)) //Ex FYI
}

@Step("Checking the entire response body after the POST request to create the User entity -> {updateBodyResp}, {updateBody}, {userPostResp} ")
fun assertFullBodyAfterPut(updateBodyResp: UserPayload, updateBody: Map<String, Any?>, userPostResp: UserPayload) {
    assertThat(updateBodyResp.name, equalTo(updateBody[NAME]))
    assertThat(updateBodyResp.email, equalTo(updateBody[EMAIL]))
    assertThat(updateBodyResp.status, equalTo(updateBody[STATUS]))
    assertThat(updateBodyResp.gender, equalTo(userPostResp.gender))
    assertThat(updateBodyResp.id, equalTo(userPostResp.id))
}

@Step("We send a POST request to create the User {body} entity and extract the request to the class ")
fun sendPostReqAndGetRespAndGetDTO(body: Map<String, Any?>): UserPayload {
    return createUserPostRequest(body).extract().body().`as`(UserPayload::class.java)
}

@Step("Sending a DELETE request to delete the User entity -> {fullUSerPath}")
fun sendDeleteReq(fullUSerPath: String): ValidatableResponse {
    return RestAssured.given().delete(fullUSerPath).then()
}






