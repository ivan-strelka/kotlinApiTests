package api.gorest.tests

import api.gorest.constants.*
import api.gorest.utils.*
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection

@DisplayName("E2E Tests")
class E2eUserTest {

    @DisplayName(
        "E2e test 1. Create user \n 2. Get created user \n 3. Update user \n " +
                "4. Get updated user \n 5. Delete updated user \n 6. Det deleted user "
    )
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun someExampleE2eTest() {
        //create user step
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyActiveFemale()
        val userResp = sendPostReqAndGetRespAndGetDTO(body)
        assertFullBodyAfterPost(userResp, body)
        val fullUserPath = getFullPathForPut(userResp)

        //get user
        val id = userResp.id
        installSpecification(requestSpec(), responseSpecOK200())
        sendGetReq(fullUserPath)
        assertFullBodyAfterPost(userResp, body)

        //update user step
        val updateBody = createActiveUserBodyPut()
        installSpecification(responseSpecOK200())

        val updateBodyResp = sendUserPutRequest(updateBody, fullUserPath).extract().jsonPath()

        assertThat(updateBodyResp.getString(NAME), equalTo(updateBody[NAME]))
        assertThat(updateBodyResp.getString(EMAIL), equalTo(updateBody[EMAIL]))
        assertThat(updateBodyResp.getString(STATUS), equalTo(updateBody[STATUS]))
        assertThat(updateBodyResp.getString(GENDER), equalTo(userResp.gender))
        assertThat(updateBodyResp.getString(ID), equalTo(id.toString()))

        //get updated user step
        installSpecification(requestSpec(), responseSpecOK200())
        val getUpdateUserBody = sendGetReq(fullUserPath).extract().jsonPath()

        assertThat(getUpdateUserBody.getString(NAME), equalTo(updateBody[NAME]))
        assertThat(getUpdateUserBody.getString(EMAIL), equalTo(updateBody[EMAIL]))
        assertThat(getUpdateUserBody.getString(STATUS), equalTo(updateBody[STATUS]))
        assertThat(getUpdateUserBody.getString(GENDER), equalTo(userResp.gender))
        assertThat(getUpdateUserBody.getString(ID), equalTo(id.toString()))

        //delete updated user step
        installSpecification(responseSpecOK204())
        sendDeleteReq(fullUserPath).assertStatusCode(HttpURLConnection.HTTP_NO_CONTENT) // Double check FYI

        //get deleted user
        installSpecification(responseSpecError404())
        val assertStatusCode =
            sendGetReq(fullUserPath).assertStatusCode(HttpURLConnection.HTTP_NOT_FOUND) // Double check FYI
        val message = assertStatusCode.extract().jsonPath().getString(MESSAGE)
        assertThat(message, equalTo(RESOURCE_NOT_FOUND))

    }
}