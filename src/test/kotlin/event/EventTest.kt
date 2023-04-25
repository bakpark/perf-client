package event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EventTest {


    // check serialize result
    @Test
    fun serializeTest() {
        val objectMapper = jacksonObjectMapper()

        assertEquals("{\"userId\":\"userId\"}", objectMapper.writeValueAsString(UserSignupEvent("userId")))
    }
}