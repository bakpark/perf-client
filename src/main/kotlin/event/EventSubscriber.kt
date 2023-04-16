package event

import event.Event

interface EventSubscriber {
    fun subscribe(event: Event)
}