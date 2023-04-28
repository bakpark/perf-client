package metric

class CounterGauge(
    var count: Int = 0
) {
    fun increment() {
        count++
    }

    fun decrement() {
        count--
    }
}