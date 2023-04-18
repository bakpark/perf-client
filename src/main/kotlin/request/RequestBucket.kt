package request

import java.time.LocalDateTime

class RequestBucket(
    requestPerSecond: Int
) {
    private val nsBetween = (1_000_000_000L / requestPerSecond)
    private var lastRequestTimestamp: LocalDateTime = LocalDateTime.now()

    fun isAvailable(): Boolean {
        val now = LocalDateTime.now()
        if (lastRequestTimestamp.isBefore(now.minusNanos(nsBetween))) {
            lastRequestTimestamp = now
            return true
        }
        return false
    }
}