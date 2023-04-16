package metric

import common.Constant
import io.micrometer.core.instrument.Meter
import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.prometheus.client.exporter.PushGateway
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*


class MicrometerMetricPusher(
    pushGwAddress: String
) : MetricCollector {
    private val log = LoggerFactory.getLogger(MicrometerMetricPusher::class.java)
    private val grouping = Collections.singletonMap("instance", Constant.instanceName)

    private val registry: PrometheusMeterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    private val pushGateway: PushGateway = PushGateway(pushGwAddress)

    init {
        // Register the registry as a global registry
        Metrics.globalRegistry.add(registry)

        JvmMemoryMetrics().bindTo(registry)
        JvmGcMetrics().bindTo(registry)
        ProcessorMetrics().bindTo(registry)

        registry.config().meterFilter(
            object : MeterFilter {
                override fun map(id: Meter.Id): Meter.Id {
                    return id.withTag(Tag.of("job", Constant.instanceName))
                }
            }
        )
        log.info("[MetricPusher] initialize pushGwAddress:{}", pushGwAddress)
    }

    override fun collect() {
        try {
            pushGateway.push(registry.prometheusRegistry, Constant.instanceName, grouping)
        } catch (e: IOException) {
            log.error("Error pushing metrics to Pushgateway: " + e.message)
        }
    }
}