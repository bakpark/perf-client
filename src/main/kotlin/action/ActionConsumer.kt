package action

interface ActionConsumer {
    fun consume(action: Action)
}