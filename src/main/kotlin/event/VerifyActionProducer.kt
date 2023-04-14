package event

import model.Model

class VerifyActionProducer(
    val model: Model
){
    fun getRoomsUserInvolved(): GetRoomUserInvolvedEvent {
        return GetRoomUserInvolvedEvent("", emptyList(), 0)
    }

    fun getChatsUserReceived(): GetChatsUserReceivedEvent {
        return GetChatsUserReceivedEvent("", 0)
    }

    fun getChatsInTheRoom(): GetChatsInTheRoom {
        return GetChatsInTheRoom("", 0)
    }
}