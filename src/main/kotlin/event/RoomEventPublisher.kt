package event

import model.Model

class RoomEventPublisher(
    val model: Model,
    val roomLimit: Int
) {
    fun publishCreateRoomEvent(): CreateRoomEvent {
        return CreateRoomEvent("", emptyList())
    }

    fun publishDeleteRoomEvent(): DeleteRoomEvent {
        return DeleteRoomEvent("")
    }

    fun publishUserEntranceEvent(): UserEntranceEvent {
        return UserEntranceEvent("", "")
    }

}