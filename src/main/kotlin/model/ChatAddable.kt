package model

import common.Constant

open class ChatAddable {
    private val chats = ArrayDeque<Chat>()

    open fun addChat(chat: Chat) {
        if (chats.size + 1 > Constant.contentLimitSize) {
            chats.removeFirst()
        }
        chats.addLast(chat)
    }

    fun getChats(): List<Chat> {
        return chats.toList()
    }
}