import common.Container
import event.EventRunner
import metric.MetricCollectRunner

fun main(args: Array<String>) {
    val container = Container().apply { init() }

    val metricCollectRunner = MetricCollectRunner(container)
    metricCollectRunner.run()

    val eventRunner = EventRunner(container)
    eventRunner.run()
}