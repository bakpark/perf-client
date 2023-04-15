package event

import model.User

interface Event

enum class EventType{
    POST_CHAT,
    CREATE_ROOM,
    DELETE_ROOM,
    USER_ENTRANCE,
    USER_SIGNUP,
    USER_WITHDRAW,
    GET_ROOM_USER_INVOLVED,
    GET_CHATS_USER_RECEIVED,
    GET_CHATS_IN_THE_ROOM
}

class PostChatEvent(
    val roomId: String,
    val senderId: String,
    val message: String
) : Event

class CreateRoomEvent(
    val roomId: String,
    val users: List<String>
) : Event

class DeleteRoomEvent(
    val roomId: String
) : Event

class UserEntranceEvent(
    val roomId: String,
    val userId: String
) : Event

class UserSignupEvent(
    val userId: String
) : Event

class UserWithdrawEvent(
    val userId: String
) : Event

class GetRoomUserInvolvedEvent(
    val userId: String,
    val limit: Int
) : Event

class GetChatsUserReceivedEvent(
    val userId: String,
    val limit: Int
) : Event

class GetChatsInTheRoom(
    val roomId: String,
    val limit: Int
) : Event