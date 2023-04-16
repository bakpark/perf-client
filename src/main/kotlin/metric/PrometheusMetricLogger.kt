package metric

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat
import io.prometheus.client.hotspot.DefaultExports
import org.slf4j.LoggerFactory
import java.io.StringWriter
import java.io.Writer

class PrometheusMetricLogger {
    private val logger = LoggerFactory.getLogger("prometheus")

    init {
        DefaultExports.initialize()
    }

    fun flush() {
        val registry = CollectorRegistry.defaultRegistry
        val writer: Writer = StringWriter()
        TextFormat.write004(writer, registry.metricFamilySamples())
        writer.flush()

        val metrics: String = writer.toString()
        logger.info(metrics)
    }
}