package api.gorest.tests

import api.gorest.constants.FEATURE_API
import api.gorest.utils.*
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import org.junit.jupiter.api.Test

class PostUserTest : BaseTest() {

    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun namePost() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyActiveFemale()
        val userResp = sendPostReqAndGetRespToDTO(body)

        assertFullBodyAfterPost(userResp, body)

    }
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun namePost1() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyInactiveFemale()
        val userResp = sendPostReqAndGetRespToDTO(body)

        assertFullBodyAfterPost(userResp, body)

    }

    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun namePost2() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyInactiveMale()
        val userResp = sendPostReqAndGetRespToDTO(body)

        assertFullBodyAfterPost(userResp, body)

    }

    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun namePost3() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyActiveMale()
        val userResp = sendPostReqAndGetRespToDTO(body)

        assertFullBodyAfterPost(userResp, body)

    }


}