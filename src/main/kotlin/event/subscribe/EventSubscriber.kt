package event.subscribe

import event.Event

interface EventSubscriber {
    fun subscribe(event: Event)
}