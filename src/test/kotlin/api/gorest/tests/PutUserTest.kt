package api.gorest.tests

import api.gorest.constants.*
import api.gorest.models.UserPayload
import api.gorest.utils.*
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

class PutUserTest : BaseTest() {

    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun putTestByJsonPath() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyInactiveMale()
        val userResp = createUserPostRequest(body)
            .extract().jsonPath()

        val id = userResp.getString(ID)

        val fullUSerPath = "$USER_ENDPOINT" + id

        val updateBody = createActiveUserBodyPut()

        installSpecification(responseSpecOK200())

        val updateBodyResp = sendUserPutRequest(updateBody, fullUSerPath).extract().jsonPath()

        MatcherAssert.assertThat(updateBodyResp.getString(NAME), Matchers.equalTo(updateBody[NAME]))
        MatcherAssert.assertThat(updateBodyResp.getString(EMAIL), Matchers.equalTo(updateBody[EMAIL]))
        MatcherAssert.assertThat(updateBodyResp.getString(STATUS), Matchers.equalTo(updateBody[STATUS]))
        MatcherAssert.assertThat(updateBodyResp.getString(GENDER), Matchers.equalTo(userResp.getString(GENDER)))
        MatcherAssert.assertThat(updateBodyResp.getString(ID), Matchers.notNullValue())


    }

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