package model

import common.randomInt
import java.util.*

class Room(
    val roomId: String,
    val users: ArrayList<User> = arrayListOf()
) : ChatAddable() {

    override fun addChat(chat: Chat) {
        users.forEach { it.addChat(chat) }
        super.addChat(chat)
    }

    fun addUser(user: User) {
        users.add(user)
    }

    fun randomUserInvolved(): User {
        val rd = randomInt(users.size)
        return users[rd]
    }

    fun contains(user: User): Boolean {
        return users.contains(user)
    }
}