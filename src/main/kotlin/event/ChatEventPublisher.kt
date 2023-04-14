package event

import model.Model

class ChatEventPublisher(
    private val model: Model
) {
    fun publishUserPostChat(): PostChatEvent {
        return PostChatEvent("", "", "")
    }
}