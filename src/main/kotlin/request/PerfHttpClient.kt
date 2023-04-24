package request

import event.Event
import io.micrometer.core.instrument.Counter
import metric.MetricCollector
import org.slf4j.LoggerFactory
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min

class PerfHttpClient(
    private val responsePostProcessor: ResponsePostProcessor,
    private val requestBucket: RequestBucket,
    private val rpsLimit: Int
) {
    private val logger = LoggerFactory.getLogger(PerfHttpClient::class.java)
    private val executor: Executor = object : ThreadPoolExecutor(
        min(rpsLimit, 10),
        max(min(rpsLimit, 10), rpsLimit / 10),
        0,
        TimeUnit.SECONDS,
        ArrayBlockingQueue(rpsLimit * 2)
    ) {}
    private val httpClient: HttpClient = HttpClient
        .newBuilder()
        .executor(executor)
        .connectTimeout(Duration.ofSeconds(10L))
        .build()
    private val requestCounter = Counter.builder("request_count")
        .register(MetricCollector.registry)
    private val failResponseCounter = Counter.builder("response_fail_count")
        .register(MetricCollector.registry)

    fun sendAsync(request: HttpRequest, event: Event) {
        val requestId = requestBucket.drawRequestId()

        responsePostProcessor.register(requestId, event)

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApplyAsync({ responsePostProcessor.processResponse(requestId, it) }, executor)
            .exceptionally {
                logger.error("http send fail", it)
                failResponseCounter.increment()
            }
        requestCounter.increment()
    }

}