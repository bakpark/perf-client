package validation

class ListStringGrader {
    fun scoring(expected: List<String>, actual: List<String>): Double {
        var unknown = 0
        var missed = 0

        val sortedActual = actual.sorted()
        val actualSize = sortedActual.size
        val sortedExpected = expected.sorted()
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