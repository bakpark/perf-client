package model

class User(
    val rooms: MutableList<Room> = mutableListOf()
) : ChatAddable() {

    fun addRoom(room: Room) {
        rooms.add(room)
    }

    fun removeRoom(room: Room){
        rooms.remove(room)
    }
}