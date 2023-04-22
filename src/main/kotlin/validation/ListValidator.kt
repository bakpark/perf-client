package validation

class ListValidator {
    private val registered = HashMap<String, List<String>>()
    fun register(requestId: String, expected: List<String>) {
        registered[requestId] = expected
    }

    fun deregister(requestId: String) {
        registered.remove(requestId)
    }

    fun validate(requestId: String, actual: List<String>): Double {
        val drawn = registered.remove(requestId) ?: return 0.0

        var unknown = 0
        var missed = 0

        val sortedActual = actual.sorted()
        val actualSize = sortedActual.size
        val sortedExpected = drawn.sorted()
        val expectedSize = sortedExpected.size

        var j = 0

        for (i in 0 until actualSize) {
            while (j < expectedSize && sortedExpected[j] < sortedActual[i]) {
                missed++
                j++
            }
            if (j < expectedSize && sortedActual[i] == sortedExpected[j]) {
                j++
            } else {
                unknown++
            }
        }
        missed += expectedSize - j

        return 1.0 * (1 + expectedSize - unknown - missed) / (1 + expectedSize)
    }

}