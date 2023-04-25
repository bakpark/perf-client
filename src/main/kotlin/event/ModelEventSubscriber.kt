package event

interface ModelEventSubscriber {
    fun subscribe(event: Event)
}