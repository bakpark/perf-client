package metric

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.PushGateway
import io.prometheus.client.hotspot.DefaultExports
import org.slf4j.LoggerFactory
import java.io.IOException


class MetricPusher(
    private val pushGwAddress: String
) : MetricCollector {
    private val log = LoggerFactory.getLogger(MetricPusher::class.java)
    init {
        DefaultExports.initialize()
        log.info("[MetricPusher] initialize pushGwAddress:{}", pushGwAddress)
    }

    override fun collect() {
        val registry = CollectorRegistry.defaultRegistry

        // Push the metrics to the Pushgateway
        val pushGateway = PushGateway(pushGwAddress)

        try {
            pushGateway.push(registry, "perf-client")
        } catch (e: IOException) {
            println("Error pushing metrics to Pushgateway: " + e.message)
        }
    }
}