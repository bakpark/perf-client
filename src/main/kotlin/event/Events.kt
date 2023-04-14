package event

import model.User

interface Event

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
    val roomId: String,
    val users: List<User>,
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