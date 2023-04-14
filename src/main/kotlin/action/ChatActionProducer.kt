package action

import model.Model

class ChatActionProducer(
    private val model: Model
) {
    fun userPostChatAction(): PostChatAction {
        return PostChatAction("", "", "")
    }
}