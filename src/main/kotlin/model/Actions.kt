package model

interface Action {
    fun invoke()
}

class CreateRoomAction(
    val roomId: Int,
    val users: List<User>
): Action {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class PostChatAction(
    val roomId: Int,
    val senderId: Int
): Action{
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class UserEntranceAction(
    val roomId: Int,
    val userId: Int
): Action{
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class RoomVerifyAction(
    val roomId: Int,
    val users: List<User>
): Action{
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class UserChatVerifyAction(
    val userId: Int,
    val limit: Int
):Action{
    override fun invoke() {
        TODO("Not yet implemented")
    }
}

class RoomChatVerifyAction(
    val roomId: Int,
    val limit: Int
):Action{
    override fun invoke() {
        TODO("Not yet implemented")
    }

}