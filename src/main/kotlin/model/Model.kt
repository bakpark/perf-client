package model

import IdGenerator
import event.*
import exception.ModelException
import java.util.*

class Model(
    private val userIdGenerator: IdGenerator,
    private val roomIdGenerator: IdGenerator
) : EventSubscriber {
    private val users = TreeMap<String, User>()
    private val rooms = TreeMap<String, Room>()

    override fun subscribe(event: Event) {
        when (event) {
            is PostChatEvent -> onPostChat(event.roomId, event.senderId, event.message)
            is CreateRoomEvent -> onCreateRoom(event.roomId, event.users)
            is DeleteRoomEvent -> onDeleteRoom(event.roomId)
            is UserEntranceEvent -> onUserEntrance(event.roomId, event.userId)
            is UserSignupEvent -> onUserSignup(event.userId)
            is UserWithdrawEvent -> onUserWithdraw(event.userId)
        }
    }

    private fun onPostChat(roomId: String, senderId: String, message: String) {
        rooms[roomId]!!.addChat(Chat(roomId, senderId, message))
    }

    private fun onCreateRoom(roomId: String, userIds: List<String>) {
        val users = userIds.map { users[it]!! }
        val room = Room(ArrayList(users))
        rooms[roomId] = room
    }

    private fun onDeleteRoom(roomId: String) {
        val room = rooms[roomId]
        room!!.users.forEach { it.removeRoom(room) }
        rooms.remove(roomId)
    }

    private fun onUserEntrance(roomId: String, userId: String) {
        val user = users[userId]!!
        val room = rooms[roomId]!!
        user.addRoom(room)
        room.addUser(user)
    }

    private fun onUserSignup(userId: String) {
        users[userId] = User()
    }

    private fun onUserWithdraw(userId: String) {
        users.remove(userId)
    }

    fun randomUser(): User {
        if(users.isEmpty()){
            throw ModelException("user is empty")
        }
        while (true){
            val entry = users.lowerEntry(userIdGenerator.generate()) ?: continue
            return entry.value
        }
    }


    fun randomRoom(): Room {
        if(rooms.isEmpty()){
            throw ModelException("room is empty")
        }
        while (true){
            val entry = rooms.lowerEntry(roomIdGenerator.generate()) ?: continue
            return entry.value
        }
    }


}