package request

import event.EventType
import generator.IdGenerator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class RequestBucketTest {

    @Test
    fun requestTest() {
        val rps = 1
        val bucket = RequestBucket(rps, IdGenerator("req"))

        bucket.activateRequest(EventType.USER_SIGNUP)
        val point_1 = LocalDateTime.now()
        bucket.activateRequest(EventType.USER_SIGNUP)
        val point_2 = LocalDateTime.now()

        println("$point_1, $point_2")

        Assertions.assertTrue(point_2.isAfter(point_1.plusSeconds(1L)))
    }

    @Test
    fun requestTest2() {
        val rps = 500
        val bucket = RequestBucket(rps, IdGenerator("req"))

        bucket.activateRequest(EventType.USER_SIGNUP)
        val point_1 = LocalDateTime.now()
        bucket.activateRequest(EventType.USER_SIGNUP)
        val point_2 = LocalDateTime.now()
        bucket.activateRequest(EventType.USER_SIGNUP)
        val point_3 = LocalDateTime.now()

        println("$point_1, $point_2 $point_3")

        Assertions.assertTrue(point_2.isAfter(point_1.plusNanos(1_000_000_000L/rps)))
        Assertions.assertTrue(point_3.isAfter(point_2.plusNanos(1_000_000_000L/rps)))
    }

}