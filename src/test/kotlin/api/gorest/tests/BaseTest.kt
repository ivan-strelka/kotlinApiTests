package api.gorest.tests

import io.restassured.RestAssured.*
import io.restassured.parsing.Parser
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class BaseTest {

    @BeforeEach
    fun setUp() {
        registerParser("text/html", Parser.JSON)
        defaultParser = Parser.JSON;
    }

    @AfterEach
    internal fun tearDown() {
        reset()
    }
}