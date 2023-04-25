package model

import event.*

class ModelEventSubscriptionForModel(
    private val model: Model
) : ModelEventSubscriber {
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
        model.rooms[roomId]!!.addChat(Chat(roomId, senderId, message))
    }

    private fun onCreateRoom(roomId: String, userIds: List<String>) {
        val users = userIds.map { model.users[it]!! }
        val room = Room(roomId, ArrayList(users))
        model.rooms[roomId] = room
    }

    private fun onDeleteRoom(roomId: String) {
        val room = model.rooms[roomId]
        room!!.users.forEach { it.removeRoom(room) }
        model.rooms.remove(roomId)
    }

    private fun onUserEntrance(roomId: String, userId: String) {
        val user = model.users[userId]!!
        val room = model.rooms[roomId]!!
        user.addRoom(room)
        room.addUser(user)
    }

    private fun onUserSignup(userId: String) {
        model.users[userId] = User(userId)
    }

    private fun onUserWithdraw(userId: String) {
        model.users.remove(userId)
    }
}