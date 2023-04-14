package event

interface EventSubscriber {
    fun subscribe(event: Event)
}