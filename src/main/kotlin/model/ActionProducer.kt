package model

import kotlin.random.Random

class ActionProducer(
    userLimit: Int,
    roomLimit: Int
) {
    val users: Array<User> = Array(userLimit) { _ -> User() }
    val rooms: List<Room> = ArrayList()
    fun produce(): Action {
        val rnd = Random(0).nextDouble()
        return if (rnd < 0.1) {
            createRoom()
        } else if (rnd < 0.5) {
            postChat()
        } else if (rnd < 0.6) {
            userEntrance()
        } else if (rnd < 0.7) {
            roomVerify()
        } else if (rnd < 0.8) {
            userChatVerify()
        } else {
            roomChatVerify()
        }
    }

    private fun createRoom(): CreateRoomAction {
        return CreateRoomAction(0, emptyList())
    }

    private fun postChat(): PostChatAction {
        return PostChatAction(0, 0)
    }

    private fun userEntrance(): UserEntranceAction {
        return UserEntranceAction(0, 0)
    }

    private fun roomVerify(): RoomVerifyAction {
        return RoomVerifyAction(0, emptyList())
    }

    private fun userChatVerify(): UserChatVerifyAction {
        return UserChatVerifyAction(0, 0)
    }

    private fun roomChatVerify(): RoomChatVerifyAction {
        return RoomChatVerifyAction(0, 0)
    }
}


class User
class Room
class Chat