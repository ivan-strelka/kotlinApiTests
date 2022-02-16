package api.gorest.tests

import api.gorest.constants.*
import api.gorest.models.UserPayload
import api.gorest.utils.*
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests method PUT /public/v2/users/:id -> `Update user details by id` ")
class PutUserTest : BaseTest() {

    @DisplayName("Check update name, email, status field and change male user")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun createActiveUserBodyTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyInactiveMale()
        val userResp = createUserPostRequest(body)
            .extract().jsonPath()

        val id = userResp.getString(ID)

        val fullUSerPath = USER_ENDPOINT + id

        val updateBody = createActiveUserBodyPut()

        installSpecification(responseSpecOK200())

        val updateBodyResp = sendUserPutRequest(updateBody, fullUSerPath).extract().jsonPath()

        assertThat(updateBodyResp.getString(NAME), equalTo(updateBody[NAME]))
        assertThat(updateBodyResp.getString(EMAIL), equalTo(updateBody[EMAIL]))
        assertThat(updateBodyResp.getString(STATUS), equalTo(updateBody[STATUS]))
        assertThat(updateBodyResp.getString(GENDER), equalTo(userResp.getString(GENDER)))
        assertThat(updateBodyResp.getString(ID), notNullValue())


    }

    @DisplayName("Check update name, email, status field and change active to inactive male user")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun changeNameEmailStatusByDTO() {
        installSpecification(requestSpec(), responseSpecOK201())

        val bodyPostReq = createUserBodyInactiveMale()
        val userPostResp = createUserPostRequest(bodyPostReq)
            .extract().body().`as`(UserPayload::class.java)

        assertFullBodyAfterPost(userPostResp, bodyPostReq)

        val fullUSerPath = getFullPathForPut(userPostResp)

        val updateBody = createActiveUserBodyPut()

        installSpecification(responseSpecOK200())

        val updateBodyResp = sendUserPutRequest(updateBody, fullUSerPath)
            .extract().body().`as`(UserPayload::class.java)

        assertFullBodyAfterPut(updateBodyResp, updateBody, userPostResp)

    }

    @DisplayName("Check update name, email, status field and change inactive to active male user")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun changeNameEmailInactiveStatusByDTO() {
        installSpecification(requestSpec(), responseSpecOK201())

        val bodyPostReq = createUserBodyInactiveMale()
        val userPostResp = createUserPostRequest(bodyPostReq)
            .extract().body().`as`(UserPayload::class.java)

        assertFullBodyAfterPost(userPostResp, bodyPostReq)

        val fullUSerPath = getFullPathForPut(userPostResp)

        val updateBody = createInactiveUserBodyPut()

        installSpecification(responseSpecOK200())

        val updateBodyResp = sendUserPutRequest(updateBody, fullUSerPath)
            .extract().body().`as`(UserPayload::class.java)

        assertFullBodyAfterPut(updateBodyResp, updateBody, userPostResp)

    }


}