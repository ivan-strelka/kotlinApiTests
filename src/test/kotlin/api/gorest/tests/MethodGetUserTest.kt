package api.gorest.tests

import api.gorest.constants.FEATURE_API
import api.gorest.constants.MESSAGE
import api.gorest.constants.USER_ENDPOINT
import api.gorest.models.UserGet
import api.gorest.utils.*
import io.qameta.allure.Description
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import org.apache.http.HttpStatus.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.net.HttpURLConnection.HTTP_NOT_ACCEPTABLE
import java.util.stream.Stream


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests method GET /public/v2/users -> `Get user details` ")
class MethodGetUserTest : BaseTest() {

    @DisplayName("Checking the size of a data array")
    @Description("Get 20 objects with fields: id, name, email, gender, status")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.NORMAL)
    @Test
    fun correctUserNumberTest() {
        installSpecification(requestSpec(), responseSpecOK200())
        val userResponse = sendGetReq("$USER_ENDPOINT")
            .assertStatusCode(SC_OK)
            .extract().jsonPath().getList<UserGet>(".", UserGet::class.java)

        assertThat(userResponse.size, equalTo(20))
        // or check `greaterThan` if we have strict requirements
        assertThat(userResponse.size, Matchers.greaterThan(15))
        assertThat(userResponse.size, Matchers.lessThan(30))
    }

    @ParameterizedTest(name = "#{index} - Incorrect path parameter in the request -> GET /user/{0}")
    @MethodSource("argsProviderFactory")
    @DisplayName("Check number validation")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    fun wrongUserPathParameterTest(input: String) {
        installSpecification(requestSpec(), responseSpecError404())
        val Response = sendGetReq("$USER_ENDPOINT$input")
            .assertStatusCode(SC_NOT_FOUND)
        val message = Response.extract().jsonPath().getString(MESSAGE)
        assertThat(message, equalTo("Resource not found"))
    }

    private fun argsProviderFactory(): Stream<String>? {
        return Stream.of(
            "0",
            "-5",
            "0x0F",
            "2147483648",
            "2_147_483_647",
            "2_147_483_648",
            "0704932840239483204982309480329409234803248032840238402384032482033824023840234",
            "!@#$%^&*&^%$#@!@#$%^&^%$#@!@#$%^&&^%$#",
            "ываывdfs№;%:?987654",
            "миру мир",
            "1 1",
            "01",
            "   1    ",
            "SDLFHB",
            "llsdfDSFLJB3vcxv",
            "1,",
            "0 1",
            ",1",
            "äöüÄÖÜß",
            "àâçéèêëîïôûùüÿ",
            "NÑO",
            "éàòùÀàÁáÈèÉéÌìÍíÒòÓóÙùÚú",
            "中国的",
            "日本の",
            "한국의",
            "`",
            "ظواهر الملحوظة",
            "ทดสอบนะจ๊",
            "क जल्दी भूरी लोमड़ी आलसी कुत्ते पर",
            "Iñtërnâtiônàlizætiøn☃\uD83D\uDCAA",
            "☃",
            "ᴮᴵᴳᴮᴵᴿᴰ",
            "null",
            "nil",
            "NaN",
            "Robert'); DROP TABLE Students;--",
            "'-prompt()-'",
            "0L"
        )
    }

    @ParameterizedTest(name = "#{index} - Incorrect path parameter in the request -> GET /user/{0} check 406 status")
    @ValueSource(
        strings = [
            "1.0", "0.1", "11.1", "1.00", "1.01", "999999999999999999999999999999999999999999.1",
            "000000000000000000001111111111111.1", "000000.1", "0.0d"
        ]
    )
    @DisplayName("Check float number validation")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    fun wrongUserIdCheckValidationTest(input: String) {
        installSpecification(requestSpec(), responseSpecError406())
        sendGetReq("$USER_ENDPOINT$input")
            .assertStatusCode(HTTP_NOT_ACCEPTABLE)
    }

    @ParameterizedTest(name = "#{index} - Check validation path parameter in the request -> GET /user/{0}")
    @ValueSource(
        strings = [
            "144L", "1_1", "\u0000"
        ]
    )
    @DisplayName("Check number and unicode validation")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    fun wrongNumberUserPathParameterTest(input: String) {
        installSpecification(requestSpec(), responseSpecOK200())
        sendGetReq("$USER_ENDPOINT$input")
            .assertStatusCode(SC_OK)
    }

    @ParameterizedTest(name = "#{index} - Check validation path parameter in the request -> GET /user/{0}")
    @ValueSource(strings = ["1.", "1/1", ".1"])
    @DisplayName("Check bite number validation")
    @Feature(FEATURE_API)
    @Severity(SeverityLevel.CRITICAL)
    fun numberValidationPathParameterTest(input: String) {
        installSpecification(requestSpec(), responseTextSpecError404())
        sendGetReq("$USER_ENDPOINT$input")
            .assertStatusCode(SC_NOT_FOUND)
    }

    @ParameterizedTest(name = "#{index} - Check validation path parameter in the request -> GET /user/{0}")
    @ValueSource(
        strings = [
            "<blink>Hello there</blink>", "Nice site, I think I'll take it. <script>alert('Executing JS')</script>"
        ]
    )
    @Feature(FEATURE_API)
    @DisplayName("Check script validation")
    @Severity(SeverityLevel.CRITICAL)
    fun scriptPathParameterTest(input: String) {
        installSpecification(requestSpec(), responseSpecError400())
        sendGetReq("$USER_ENDPOINT$input")
            .assertStatusCode(SC_BAD_REQUEST)
    }

}