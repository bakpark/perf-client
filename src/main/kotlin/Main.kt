import metric.MetricCollectRunner

fun main(args: Array<String>) {
    val runner = Runner()

    MetricCollectRunner().run()

    while (true) {
        runner.run()
    }
}