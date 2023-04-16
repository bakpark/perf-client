package metric

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MetricCollectRunner {
    private lateinit var metricCollector: MetricCollector
    fun run() {
        val pushGwAddress: String = System.getProperty("prometheus.gw_address") ?: return
        val prometheusInterval: Long = System.getProperty("prometheus.interval")?.toLong() ?: 15L

        metricCollector = MicrometerMetricPusher(pushGwAddress = pushGwAddress)

        // 지표를 주기적으로 출력하는 스케줄러 생성
        val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
        scheduler.scheduleAtFixedRate(metricCollector::collect, 0, prometheusInterval, TimeUnit.SECONDS)
    }
}