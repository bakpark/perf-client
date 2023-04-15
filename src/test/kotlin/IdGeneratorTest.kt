import common.addUUID
import generator.IdGenerator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IdGeneratorTest {
    @Test
    fun test() {
        val workerId = "".addUUID(5)
        repeat(10) { trial ->
            val generator = IdGenerator("$workerId-user-")
            val list = arrayListOf<String>()
            val size = 1_000_000
            repeat(size) { list.add(generator.generate()) }
            list.sort()
            for (i in 1 until size) {
                if (list[i - 1] == list[i]) {
                    Assertions.fail<Nothing>("exist same ids in trial:$trial i:$i ids:${list[i]}")
                }
            }
        }
    }
}