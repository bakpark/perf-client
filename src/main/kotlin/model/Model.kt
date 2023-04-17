package model

import exception.ModelException
import generator.IdGenerator
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

    fun randomUser(n: Int): List<User> {
        if (users.size < n) {
            throw ModelException("input exceed user size")
        }
        val userList = mutableListOf<User>()
        while (userList.size < n) {
            val usr = randomUser()
            if (userList.contains(usr)) {
                continue
            }
            userList.add(usr)
        }
        return userList
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

    fun randomRoomSize(): Int {
        if (rooms.isEmpty()) {
            return 0
        }
        return randomRoom().users.size
    }

}