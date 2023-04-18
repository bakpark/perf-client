package common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UtilTest {

    @Test
    fun workerId() {
        val tryCnt = 1000
        var failLimit = 10
        repeat(tryCnt) {
            if (existSameId()) {
                --failLimit
            }
        }
        if (failLimit < 0) {
            Assertions.fail<Nothing>("in $tryCnt trial same id occur ${10 - failLimit} times")
        }
    }

    @Test
    fun minusNanos(){
        val requestPerSecond = 100
        val nsBetween = (1_000_000_000L) / requestPerSecond

        val now = LocalDateTime.now()
        val last = now.minusNanos(nsBetween)

        Assertions.assertEquals(nsBetween, now.minusNanos(last))
    }

    private fun existSameId(): Boolean {
        val size = 100
        val uuidList = arrayListOf<String>()
        repeat(size) {
            uuidList.add("".addUUID(5))
        }
        uuidList.sort()
        for (i in 1 until size) {
            if (uuidList[i - 1] >= uuidList[i]) {
                return true
            }
        }
        return false
    }
}