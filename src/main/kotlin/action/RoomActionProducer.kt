package action

class RoomActionProducer(val roomLimit: Int) {
    fun createRoomAction(): CreateRoomAction {
        return CreateRoomAction("", emptyList())
    }

    fun deleteRoomAction(): DeleteRoomAction {
        return DeleteRoomAction("")
    }

    fun userEntranceAction(): UserEntranceAction {
        return UserEntranceAction("", "")
    }

}