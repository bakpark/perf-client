package request

import common.minusNanos
import generator.IdGenerator
import java.time.LocalDateTime

class RequestBucket(
    requestPerSecond: Int,
    private val requestIdGenerator: IdGenerator
) {
    private val nsBetween = (1_000_000_000L / requestPerSecond)
    private var last: LocalDateTime = LocalDateTime.now()

    /**
     * thread sleep until consuming
     */
    fun drawRequestId(): String {
        synchronized(last) {
            val now = LocalDateTime.now()
            if (last.isBefore(now.minusNanos(nsBetween))) {
                last = now
                return requestIdGenerator.generate()
            }
            val diff = last.plusNanos(nsBetween).minusNanos(LocalDateTime.now())
            if (diff > 0) {
                Thread.sleep(diff / 1_000_000, (diff % 1_000_000L).toInt())
            }
            last = LocalDateTime.now()
            return requestIdGenerator.generate()
        }
    }
}