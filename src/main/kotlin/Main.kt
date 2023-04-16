import metric.MetricCollector

fun main(args: Array<String>) {
    val runner = Runner()

    MetricCollector().run()

    repeat(1000) {
        runner.run()
    }
}