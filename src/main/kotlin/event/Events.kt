package event

interface Event {
    fun type(): EventType
}

enum class EventType {
    POST_CHAT,
    CREATE_ROOM,
    DELETE_ROOM,
    USER_ENTRANCE,
    USER_SIGNUP,
    USER_WITHDRAW,
    GET_ROOMS_USER_INVOLVED,
    GET_CHATS_USER_RECEIVED,
    GET_CHATS_IN_THE_ROOM;
}

class PostChatEvent(
    val roomId: String,
    val senderId: String,
    val message: String
) : Event {
    override fun type(): EventType = EventType.POST_CHAT
}

class CreateRoomEvent(
    val roomId: String,
    val users: List<String>
) : Event {
    override fun type(): EventType = EventType.CREATE_ROOM
}

class DeleteRoomEvent(
    val roomId: String
) : Event {
    override fun type(): EventType = EventType.DELETE_ROOM
}

class UserEntranceEvent(
    val roomId: String,
    val userId: String
) : Event {
    override fun type(): EventType = EventType.USER_ENTRANCE
}

class UserSignupEvent(
    val userId: String
) : Event {
    override fun type(): EventType = EventType.USER_SIGNUP
}

class UserWithdrawEvent(
    val userId: String
) : Event {
    override fun type(): EventType = EventType.USER_WITHDRAW
}

class GetRoomsUserInvolvedEvent(
    val userId: String,
    val limit: Int
) : Event {
    override fun type(): EventType = EventType.GET_ROOMS_USER_INVOLVED
}

class GetChatsUserReceivedEvent(
    val userId: String,
    val limit: Int
) : Event {
    override fun type(): EventType = EventType.GET_CHATS_USER_RECEIVED
}

class GetChatsInTheRoom(
    val roomId: String,
    val limit: Int
) : Event {
    override fun type(): EventType = EventType.GET_CHATS_IN_THE_ROOM
}