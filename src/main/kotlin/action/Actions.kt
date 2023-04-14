package action

import model.User

interface Action {
    fun invoke()
}

class PostChatAction(
    val roomId: String,
    val senderId: String,
    val message: String
) : Action {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class CreateRoomAction(
    val roomId: String,
    val users: List<String>
) : Action {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class DeleteRoomAction(
    val roomId: String
): Action{
    override fun invoke() {
        TODO("Not yet implemented")
    }

}

class UserEntranceAction(
    val roomId: String,
    val userId: String
) : Action {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class UserSignupAction(
    val userId: String
) : Action {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class UserWithdrawAction(
    val userId: String
) : Action {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class GetRoomUserInvolvedAction(
    val roomId: String,
    val users: List<User>,
    val limit: Int
) : Action {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class GetChatsUserReceivedAction(
    val userId: String,
    val limit: Int
) : Action {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class GetChatsInTheRoom(
    val roomId: String,
    val limit: Int
) : Action {
    override fun invoke() {
        TODO("Not yet implemented")
    }

}