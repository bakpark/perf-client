package metric

import common.Constant
import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics
import io.micrometer.core.instrument.Meter
import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.slf4j.LoggerFactory


interface MetricCollector {
    companion object {
        val logger = LoggerFactory.getLogger(MetricCollector::class.java)
        val registry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

        init {
            Metrics.globalRegistry.add(registry)

            JvmMemoryMetrics().bindTo(registry)
            JvmGcMetrics().bindTo(registry)
            ProcessorMetrics().bindTo(registry)
            ProcessMemoryMetrics().bindTo(registry)
            ProcessThreadMetrics().bindTo(registry)

            registry.config().meterFilter(
                object : MeterFilter {
                    override fun map(id: Meter.Id): Meter.Id {
                        return id.withTag(Tag.of("application", Constant.applicationName))
                    }
                }
            )
        }

        fun create(prometheusPushUrl: String?): MetricCollector {
            if (prometheusPushUrl == null) {
                return EmptyCollector()
            }
            return try {
                MicrometerMetricPusher(prometheusPushUrl)
            } catch (e: Exception) {
                logger.error("invalid prometheus pushgw url url:{}", prometheusPushUrl, e)
                EmptyCollector()
            }
        }
    }

    fun collect()
}