package validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ListStringGraderTest {

    @Test
    @DisplayName("expected: 0 ok: 0 missed: 0 unknown: 0")
    fun validateTest1() {
        val validator = ListStringGrader()

        val expectedList = emptyList<String>()
        val actualList = emptyList<String>()

        val score = validator.scoring(expectedList, actualList)
        Assertions.assertEquals(1.0, score)
    }

    @Test
    @DisplayName("expected: 0 ok: 0 missed: 0 unknown: 1")
    fun validateTest2() {
        val validator = ListStringGrader()

        val expectedList = emptyList<String>()
        val actualList = arrayListOf<String>("1")

        val score = validator.scoring(expectedList, actualList)
        Assertions.assertEquals(1.0 * (1 + 0 - 0 - 1) / (1 + 0), score) // 0.0
    }

    @Test
    @DisplayName("expected: 0 ok: 0 missed: 0 unknown: 3")
    fun validateTest3() {
        val validator = ListStringGrader()

        val expectedList = emptyList<String>()
        val actualList = arrayListOf<String>("1", "2", "1.5")

        val score = validator.scoring(expectedList, actualList)
        Assertions.assertEquals(1.0 * (1 + 0 - 0 - 3) / (1 + 0), score) // -2.0
    }

    @Test
    @DisplayName("expected: 10 ok: 10 missed: 0 unknown: 0")
    fun validateTest4() {
        val validator = ListStringGrader()

        val expectedList = arrayListOf("1", "2", "1.5", "0.5", "0", "2.5", "3", "4.5", "4", "3.5")
        val actualList = arrayListOf("0", "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5")

        val score = validator.scoring(expectedList, actualList)
        Assertions.assertEquals(1.0 * (1 + 10 - 0 - 0) / (1 + 10), score) // 1.0
    }

    @Test
    @DisplayName("expected: 10 ok: 9 missed: 1 unknown: 0")
    fun validateTest5() {
        val validator = ListStringGrader()

        val expectedList = arrayListOf("1", "2", "1.5", "0.5", "0", "2.5", "3", "4.5", "4", "3.5")
        val actualList = arrayListOf("0", "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4")

        val score = validator.scoring(expectedList, actualList)
        Assertions.assertEquals(1.0 * (1 + 10 - 1 - 0) / (1 + 10), score) // 9/11
    }

    @Test
    @DisplayName("expected: 10 ok: 9 missed: 1 unknown: 1")
    fun validateTest6() {
        val validator = ListStringGrader()

        val expectedList = arrayListOf("1", "2", "1.5", "0.5", "0", "2.5", "3", "4.5", "4", "3.5")
        val actualList = arrayListOf("2", "1.5", "0.5", "0", "2.5", "3", "4.5", "4", "3.5", "5")

        val score = validator.scoring(expectedList, actualList)
        Assertions.assertEquals(1.0 * (1 + 10 - 1 - 1) / (1 + 10), score) // 8/11
    }
}