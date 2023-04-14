package model

import action.*
import exception.ModelException
import java.util.*

class Model : ActionConsumer {
    private val users = TreeMap<String, User>()
    private val rooms = TreeMap<String, Room>()

    override fun consume(action: Action) {
        when (action) {
            is PostChatAction -> onPostChat(action.roomId, action.senderId, action.message)
            is CreateRoomAction -> onCreateRoom(action.roomId, action.users)
            is DeleteRoomAction -> onDeleteRoom(action.roomId)
            is UserEntranceAction -> onUserEntrance(action.roomId, action.userId)
            is UserSignupAction -> onUserSignup(action.userId)
            is UserWithdrawAction -> onUserWithdraw(action.userId)
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

    fun randomUser(upperBound: String) = users.lowerKey(upperBound) ?: throw ModelException("matched user not exist")

    fun randomRoom(upperBound: String) = rooms.lowerKey(upperBound) ?: throw ModelException("matched room not exist")

}