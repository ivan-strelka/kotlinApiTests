package api.gorest.tests

import api.gorest.constants.FEATURE_API
import api.gorest.constants.MESSAGE
import api.gorest.utils.*
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_NO_CONTENT

class DeleteUserTest : BaseTest() {

    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun nameDel() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyActiveFemale()
        val userResp = sendPostReqAndGetRespAndGetDTO(body)
        assertFullBodyAfterPost(userResp, body)
        val fullUSerPath = getFullPathForPut(userResp)

        installSpecification(responseSpecOK204())
        sendDeleteReq(fullUSerPath).assertStatusCode(HTTP_NO_CONTENT) // Double check FYI

        installSpecification(responseSpecError404())
        val assertStatusCode = sendGetReq(fullUSerPath).assertStatusCode(HTTP_NOT_FOUND) // Double check FYI
        val message = assertStatusCode.extract().jsonPath().getString(MESSAGE)
        MatcherAssert.assertThat(message, Matchers.equalTo("Resource not found"))

    }


}