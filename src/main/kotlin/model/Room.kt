package model

class Room(
    val users: ArrayList<User> = arrayListOf()
):ChatAddable() {

    override fun addChat(chat: Chat) {
        users.forEach { it.addChat(chat) }
        super.addChat(chat)
    }
    fun addUser(user: User){
        users.add(user)
    }
}