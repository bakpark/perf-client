package request

import common.minusNanos
import event.EventType
import generator.IdGenerator
import io.micrometer.core.instrument.Gauge
import metric.CounterGauge
import metric.MetricCollector
import java.time.LocalDateTime

class RequestBucket(
    requestPerSecond: Int,
    private val requestIdGenerator: IdGenerator
) {
    private val nsBetween = (1_000_000_000L / requestPerSecond)
    private var last: LocalDateTime = LocalDateTime.now()
    private val gaugeMap: HashMap<EventType, CounterGauge> = HashMap()

    init {
        EventType.values().forEach {
            gaugeMap[it] = CounterGauge()
            Gauge.builder("active_request_count") { gaugeMap[it]!!.count }
                .tag("type", it.name)
                .register(MetricCollector.registry)
        }
    }

    /**
     * thread sleep until consuming
     */
    fun activateRequest(eventType: EventType): ActiveRequest {
        synchronized(last) {
            val now = LocalDateTime.now()
            if (last.isBefore(now.minusNanos(nsBetween))) {
                last = now
                return createActiveRequest(eventType)
            }
            val diff = last.plusNanos(nsBetween).minusNanos(LocalDateTime.now())
            if (diff > 0) {
                Thread.sleep(diff / 1_000_000, (diff % 1_000_000L).toInt())
            }
            last = LocalDateTime.now()
            return createActiveRequest(eventType)
        }
    }

    fun deactivateRequest(activeRequest: ActiveRequest) {
        gaugeMap[activeRequest.eventType]!!.decrement()
    }

    private fun createActiveRequest(eventType: EventType): ActiveRequest {
        gaugeMap[eventType]!!.increment()
        return ActiveRequest(requestIdGenerator.generate(), eventType, LocalDateTime.now())
    }
}