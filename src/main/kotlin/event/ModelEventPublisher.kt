package event

import metric.EventSubscriptionForMetric
import model.EventSubscriptionForModel
import request.EventSubscriptionForRequest

class ModelEventPublisher(
    private val eventSubscriptionForModel: EventSubscriptionForModel,
    private val eventSubscriptionForRequest: EventSubscriptionForRequest,
    private val eventSubscriptionForMetric: EventSubscriptionForMetric
) {
    fun publish(event: Event) {
        eventSubscriptionForModel.subscribe(event)
        eventSubscriptionForRequest.subscribe(event)
        eventSubscriptionForMetric.subscribe(event)
    }
}