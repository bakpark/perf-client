package metric

import common.Constant
import io.prometheus.client.exporter.PushGateway
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*


class MicrometerMetricPusher(
    pushGwAddress: String
) : MetricCollector {
    private val log = LoggerFactory.getLogger(MicrometerMetricPusher::class.java)
    private val grouping = Collections.singletonMap("application", Constant.instanceName)

    private val pushGateway: PushGateway = PushGateway(pushGwAddress)

    override fun collect() {
        try {
            pushGateway.pushAdd(MetricCollector.registry.prometheusRegistry, Constant.instanceName, grouping)
        } catch (e: IOException) {
            log.error("Error pushing metrics to Pushgateway: " + e.message, e)
        }
    }
}