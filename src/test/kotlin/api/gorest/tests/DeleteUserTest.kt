package api.gorest.tests

import api.gorest.constants.FEATURE_API
import api.gorest.constants.MESSAGE
import api.gorest.constants.RESOURCE_NOT_FOUND
import api.gorest.utils.*
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_NO_CONTENT

@DisplayName("Tests method DELETE  /public/v2/users/:id -> `Delete user by id` ")
class DeleteUserTest : BaseTest() {

    @DisplayName("Check delete previously created user")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun deletePreviouslyCreatedUserTest() {
        //create user
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyActiveFemale()
        val userResp = sendPostReqAndGetRespAndGetDTO(body)
        assertFullBodyAfterPost(userResp, body)
        val fullUSerPath = getFullPathForPut(userResp)

        //delete user
        installSpecification(responseSpecOK204())
        sendDeleteReq(fullUSerPath).assertStatusCode(HTTP_NO_CONTENT) // Double check FYI

        //get user
        installSpecification(responseSpecError404())
        val assertStatusCode = sendGetReq(fullUSerPath).assertStatusCode(HTTP_NOT_FOUND) // Double check FYI
        val message = assertStatusCode.extract().jsonPath().getString(MESSAGE)
        MatcherAssert.assertThat(message, Matchers.equalTo(RESOURCE_NOT_FOUND))

    }


}