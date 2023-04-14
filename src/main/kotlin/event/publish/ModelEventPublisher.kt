package event.publish

import event.Event
import event.subscribe.ModelEventSubscription
import event.subscribe.RequestEventSubscription

class ModelEventPublisher(
    val modelEventSubscription: ModelEventSubscription,
    val requestEventSubscription: RequestEventSubscription
) {
    fun publish(event: Event) {
        modelEventSubscription.subscribe(event)
        requestEventSubscription.subscribe(event)
    }
}