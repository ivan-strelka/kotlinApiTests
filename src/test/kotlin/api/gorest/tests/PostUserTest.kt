package api.gorest.tests

import api.gorest.constants.*
import api.gorest.utils.*
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // for Parametrize test ??????????????
@DisplayName("Tests method POST /public/v2/users -> `Create a new user` ")
class PostUserTest : BaseTest() {

    @DisplayName("Check create active female user")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun createActiveFemaleUserTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyActiveFemale()
        val userResp = sendPostReqAndGetRespAndGetDTO(body)

        assertFullBodyAfterPost(userResp, body)

    }

    @DisplayName("Check create inactive female user")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun createInactiveFemaleUserTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyInactiveFemale()
        val userResp = sendPostReqAndGetRespAndGetDTO(body)

        assertFullBodyAfterPost(userResp, body)

    }

    @DisplayName("Check create inactive male user")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun createInactiveMaleUserTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyInactiveMale()
        val userResp = sendPostReqAndGetRespAndGetDTO(body)

        assertFullBodyAfterPost(userResp, body)
    }

    @DisplayName("Check create active male user")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun createActiveMaleUserTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val body = createUserBodyActiveMale()
        val userResp = sendPostReqAndGetRespAndGetDTO(body)

        assertFullBodyAfterPost(userResp, body)
    }

    @DisplayName("Send body with parameter only name")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendOnlyNameParameterTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(NAME to getRandomName())
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(EMAIL))
        assertThat(jsonPathResp.getString(I_FIELD), equalTo(GENDER))
        assertThat(jsonPathResp.getString(II_FIELD), equalTo(STATUS))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(I_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(II_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body with parameter only id")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendOnlyIdParameterTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(ID to "TEST")
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(EMAIL))
        assertThat(jsonPathResp.getString(I_FIELD), equalTo(NAME))
        assertThat(jsonPathResp.getString(II_FIELD), equalTo(GENDER))
        assertThat(jsonPathResp.getString(III_FIELD), equalTo(STATUS))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(I_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(II_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(III_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send empty body")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendEmptyBodyTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf("" to "")
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(EMAIL))
        assertThat(jsonPathResp.getString(I_FIELD), equalTo(NAME))
        assertThat(jsonPathResp.getString(II_FIELD), equalTo(GENDER))
        assertThat(jsonPathResp.getString(III_FIELD), equalTo(STATUS))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(I_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(II_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(III_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body with parameter only email")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendOnlyEmailParameterTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(EMAIL to getRandomEmail())
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(NAME))
        assertThat(jsonPathResp.getString(I_FIELD), equalTo(GENDER))
        assertThat(jsonPathResp.getString(II_FIELD), equalTo(STATUS))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(I_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(II_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body with parameter only female gender")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendOnlyFemaleGenderParameterTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(GENDER to getGenderFemale())
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(EMAIL))
        assertThat(jsonPathResp.getString(I_FIELD), equalTo(NAME))
        assertThat(jsonPathResp.getString(II_FIELD), equalTo(STATUS))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(I_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(II_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body with parameter only male gender")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendOnlyMaleGenderParameterTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(GENDER to getGenderMale())
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(EMAIL))
        assertThat(jsonPathResp.getString(I_FIELD), equalTo(NAME))
        assertThat(jsonPathResp.getString(II_FIELD), equalTo(STATUS))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(I_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(II_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body with parameter only active status")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendOnlyActiveStatusParameterTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(STATUS to getActiveStatus())
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(EMAIL))
        assertThat(jsonPathResp.getString(I_FIELD), equalTo(NAME))
        assertThat(jsonPathResp.getString(II_FIELD), equalTo(GENDER))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(I_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(II_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body with parameter only inactive status")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendOnlyInactiveStatusParameterTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(STATUS to getInactiveStatus())
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(EMAIL))
        assertThat(jsonPathResp.getString(I_FIELD), equalTo(NAME))
        assertThat(jsonPathResp.getString(II_FIELD), equalTo(GENDER))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(I_MESSAGE), equalTo(CANT_BE_BLANK))
        assertThat(jsonPathResp.getString(II_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body without parameter status")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendWithoutParameterStatusTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(
            GENDER to getGenderMale(),
            NAME to getRandomName(),
            EMAIL to getRandomEmail()
        )
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(STATUS))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body without parameter email")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendWithoutParameterEmailTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(
            GENDER to getGenderMale(),
            NAME to getRandomName(),
            STATUS to getActiveStatus()
        )
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(EMAIL))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body without parameter gender")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendWithoutParameterGenderTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(
            EMAIL to getRandomEmail(),
            NAME to getRandomName(),
            STATUS to getActiveStatus()
        )
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(GENDER))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send body without parameter name")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendWithoutParameterNameTest() {
        installSpecification(requestSpec(), responseSpecError422())
        val body = mapOf(
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()
        )
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(NAME))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(CANT_BE_BLANK))
    }

    @DisplayName("Send broke JSON body ")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun sendBrokeJSONFormatTest() {
        installSpecification(requestSpec(), responseSpecError401())
        val body = "{" + " \"name\": \"TEST\"," + "}"
        val userResp = createUserPostRequest(body)
        val jsonPathResp = userResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(MESSAGE), equalTo("Authentication failed"))
    }


    @DisplayName("Checking integer data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingIntDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to 121233,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME].toString()))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking integer max  data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingIntMaxDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to Int.MAX_VALUE,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME].toString()))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking integer min  data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingIntMinDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to Int.MIN_VALUE,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME].toString()))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking long min  data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingLongMinDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to Long.MIN_VALUE,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME].toString()))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking long max  data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingLongMaxDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to Long.MAX_VALUE,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME].toString()))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking 0 value of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingZeroValueNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to 0,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME].toString()))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking float max  data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingFloatMaxDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to Float.MAX_VALUE,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo("3.4028235e+38"))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking float min  data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingFloatMInDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to Float.MIN_VALUE,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name?.toUpperCase() ?: "", equalTo(bodyPostReq[NAME].toString()))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking double min data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingDoubleMInDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to Double.MIN_VALUE,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo("5.0e-324"))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking double min data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingDoubleMaxDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to Double.MAX_VALUE,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name?.toUpperCase() ?: "", equalTo("1.7976931348623157E+308"))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking boolean true data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingBooleanTrueDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to true,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo("t"))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking boolean false data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingBooleanFalseDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to false,
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo("f"))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking char false data types of the name field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
//    @Test
    internal fun checkingCharDataTypesNameFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val bodyPostReq = mapOf(
            NAME to "\u0000",
            EMAIL to getRandomEmail(),
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(""))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking dot in the address email field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingEmailFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val email = "firstname.lastname" + getRandomInt() + "@domain.com"
        val bodyPostReq = mapOf(
            NAME to getRandomName(),
            EMAIL to email,
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME]))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking subdomain in the address email field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingSubdomainnEmailFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val email = "email" + getRandomInt() + "@subdomain.domain.com"
        val bodyPostReq = mapOf(
            NAME to getRandomName(),
            EMAIL to email,
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME]))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking Plus in the address email field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingPlusEmailFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val email = "firstname+lastname" + getRandomInt() + "@domain.com"
        val bodyPostReq = mapOf(
            NAME to getRandomName(),
            EMAIL to email,
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME]))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking Numeric domain in the address email field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingNumericDomainEmailFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val email = "email" + getRandomInt() + "@123.123.123.123"
        val bodyPostReq = mapOf(
            NAME to getRandomName(),
            EMAIL to email,
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME]))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @DisplayName("Checking Dash in domain in the address email field")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    @Test
    internal fun checkingDashDomainEmailFieldTest() {
        installSpecification(requestSpec(), responseSpecOK201())
        val email = "email" + getRandomInt() + "@domain-one.com"
        val bodyPostReq = mapOf(
            NAME to getRandomName(),
            EMAIL to email,
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()

        )
        val userPostResp = sendPostReqAndGetRespAndGetDTO(bodyPostReq)

        assertThat(userPostResp.name, equalTo(bodyPostReq[NAME]))
        assertThat(userPostResp.email, equalTo(bodyPostReq[EMAIL]))
        assertThat(userPostResp.gender, equalTo(bodyPostReq[GENDER]))
        assertThat(userPostResp.status, equalTo(bodyPostReq[STATUS]))
        assertThat(userPostResp.id, Matchers.notNullValue())
    }

    @ParameterizedTest(name = "#{index} - Incorrect email in email field POST request -> email - {0}")
    @MethodSource("argsProviderFactory")
    @DisplayName("Check email validation")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    fun checkingValidationEmailFieldTest(input: String) {
        installSpecification(requestSpec(), responseSpecError422())
        val bodyPostReq = mapOf(
            NAME to getRandomName(),
            EMAIL to input,
            GENDER to getGenderMale(),
            STATUS to getActiveStatus()
        )

        val userPostResp = createUserPostRequest(bodyPostReq)
        val jsonPathResp = userPostResp.extract().jsonPath()

        assertThat(jsonPathResp.getString(O_FIELD), equalTo(EMAIL))
        assertThat(jsonPathResp.getString(O_MESSAGE), equalTo(INVANID_EMAIL))
    }

    private fun argsProviderFactory(): Stream<String>? {
        return Stream.of(
            "@domain.com",
            "email.domain.com",
            "plainaddress",
            "mail@学生优惠.com",
            "#@%^%#$@#$@#.com",
            "Joe Smith <email@domain.com>",
            "email@domain.com (Joe Smith)",
            "email@domain@domain.com",
            "あいうえお@domain.com",
            "email@-domain.com",
            "email@.domain.com",
            "email@domain..com"
        )
    }

}