package model

open class ChatAddable {
    val chats = ArrayDeque<Chat>()

    open fun addChat(chat: Chat){
        if(chats.size + 1> Constant.contentLimitSize){
            chats.removeFirst()
        }
        chats.addLast(chat)
    }
}