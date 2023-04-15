package event.publish

import event.Event
import event.subscribe.ModelEventSubscription
import event.subscribe.RequestEventSubscription

class ModelEventPublisher(
    private val modelEventSubscription: ModelEventSubscription,
    private val requestEventSubscription: RequestEventSubscription
) {
    fun publish(event: Event) {
        modelEventSubscription.subscribe(event)
        requestEventSubscription.subscribe(event)
    }
}