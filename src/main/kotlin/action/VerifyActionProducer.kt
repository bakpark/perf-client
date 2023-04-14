package action

class VerifyActionProducer {
    fun getRoomsUserInvolved(): GetRoomUserInvolvedAction {
        return GetRoomUserInvolvedAction("", emptyList(), 0)
    }

    fun getChatsUserReceived(): GetChatsUserReceivedAction {
        return GetChatsUserReceivedAction("", 0)
    }

    fun getChatsInTheRoom(): GetChatsInTheRoom {
        return GetChatsInTheRoom("", 0)
    }
}