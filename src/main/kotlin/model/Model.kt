package model

import generator.IdGenerator
import exception.ModelException
import java.util.*

class Model(
    private val userIdGenerator: IdGenerator,
    private val roomIdGenerator: IdGenerator
) {
    val users = TreeMap<String, User>()
    val rooms = TreeMap<String, Room>()

    fun randomUser(): User {
        if (users.isEmpty()) {
            throw ModelException("user is empty")
        }
        while (true) {
            val entry = users.lowerEntry(userIdGenerator.generate()) ?: continue
            return entry.value
        }
    }


    fun randomRoom(): Room {
        if (rooms.isEmpty()) {
            throw ModelException("room is empty")
        }
        while (true) {
            val entry = rooms.lowerEntry(roomIdGenerator.generate()) ?: continue
            return entry.value
        }
    }

}