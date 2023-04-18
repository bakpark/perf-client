package common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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