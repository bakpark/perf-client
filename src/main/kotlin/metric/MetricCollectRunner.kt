package metric

import common.Container
import common.Runner
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MetricCollectRunner(container: Container) : Runner {
    private val metricCollector = container.metricCollector
    private val collectInterval = 15L
    override fun run() {
        // 지표를 주기적으로 출력하는 스케줄러 생성
        val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
        scheduler.scheduleAtFixedRate(metricCollector::collect, 0, collectInterval, TimeUnit.SECONDS)
    }
}