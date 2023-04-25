package event

import metric.ModelEventSubscriptionForMetric
import model.ModelEventSubscriptionForModel
import request.ModelEventSubscriptionForRequest

class ModelEventPublisher(
    private val eventSubscriptionForModel: ModelEventSubscriptionForModel,
    private val eventSubscriptionForRequest: ModelEventSubscriptionForRequest,
    private val eventSubscriptionForMetric: ModelEventSubscriptionForMetric
) {
    fun publish(event: Event) {
        eventSubscriptionForModel.subscribe(event)
        eventSubscriptionForRequest.subscribe(event)
        eventSubscriptionForMetric.subscribe(event)
    }
}