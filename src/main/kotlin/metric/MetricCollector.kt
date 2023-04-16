package metric

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MetricCollector(
    private val prometheusMetricLogger: PrometheusMetricLogger = PrometheusMetricLogger()
) {

    fun run() {
        // 지표를 주기적으로 출력하는 스케줄러 생성
        val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
        scheduler.scheduleAtFixedRate(prometheusMetricLogger::flush, 0, 15, TimeUnit.SECONDS)
    }
}