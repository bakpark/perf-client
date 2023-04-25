package metric

import event.Event
import event.EventType
import event.ModelEventSubscriber
import io.micrometer.core.instrument.Counter

class ModelEventSubscriptionForMetric : ModelEventSubscriber {
    private val counters: HashMap<EventType, Counter> = HashMap()

    init {
        EventType.values().forEach {
            counters[it] = Counter.builder("event_count")
                .tag("type", it.name)
                .register(MetricCollector.registry)
        }
    }

    override fun subscribe(event: Event) {
        counters[event.type()]!!.increment()
    }
}