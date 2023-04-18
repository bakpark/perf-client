package metric

import common.Constant
import io.prometheus.client.exporter.PushGateway
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*


class MicrometerMetricPusher(prometheusPushUrl: String) : MetricCollector {
    private val log = LoggerFactory.getLogger(MicrometerMetricPusher::class.java)
    private val grouping = Collections.singletonMap("application", Constant.applicationName)
    private val pushGateway: PushGateway = PushGateway(prometheusPushUrl)

    override fun collect() {
        try {
            pushGateway.pushAdd(MetricCollector.registry.prometheusRegistry, Constant.applicationName, grouping)
        } catch (e: IOException) {
            log.error("Error pushing metrics to Push gateway: " + e.message, e)
        }
    }
}