package model

import generator.IdGenerator
import common.addUUID
import exception.ModelException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ModelTest {

    @Test
    @DisplayName("when user is empty throw ModelException")
    fun randomUserTest1() {
        val userIdGenerator = IdGenerator("${"".addUUID(5)}-user-")
        val model = Model(userIdGenerator, userIdGenerator)
        assertThrows<ModelException> { model.randomUser() }
    }

    @Test
    @DisplayName("when ok")
    fun randomUserTest2() {
        val userIdGenerator = IdGenerator("${"".addUUID(5)}-user-")
        val model = Model(userIdGenerator, userIdGenerator)
        val id = userIdGenerator.generate()
        model.users[id] = User(id)
        val randomUser = model.randomUser()
        println(randomUser)
    }

    @Test
    @DisplayName("randomness")
    fun randomUserTest3() {
        val userIdGenerator = IdGenerator("${"".addUUID(5)}-user-")
        val model = Model(userIdGenerator, userIdGenerator)
        repeat(10000) {
            val id = userIdGenerator.generate()
            model.users[id] = User(id)
        }
        val map = HashMap<Int, Int>()
        repeat(10000) {
            map.compute(
                model.randomUser().hashCode()
            ) { _, v -> if (v == null) 1 else v + 1 }
        }
        map.forEach { (_, v) ->
            if (v > 20) {
                Assertions.fail<Nothing>("Randomness cannot be guaranteed")
            }
        }
    }

}