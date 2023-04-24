package response

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RoomsResponseTest {

    @Test
    fun deserializeTest() {
        val response = "{\"rooms\":[\"ad0bc-room-88427682-05ee-48\",\"ad0bc-room-d5582c4b-a992-43\"]}"
        val obj = jacksonObjectMapper().readValue(response, RoomsResponse::class.java)
        Assertions.assertEquals(2, obj.rooms.size)
    }
}