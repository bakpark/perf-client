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
    private val responseScoreMarker: ResponseScoreMarker,
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
    private val successResponseCounter = Counter.builder("response_success_count")
        .register(MetricCollector.registry)
    private val failResponseCounter = Counter.builder("response_fail_count")
        .register(MetricCollector.registry)

    /**
     * The rate of the request is throttled by the requestBucket.
     */
    fun sendAsync(request: HttpRequest, event: Event) {
        val activeRequest = requestBucket.activateRequest(event.type())

        val expected = responseScoreMarker.getExpectedResult(event)

        requestCounter.increment()
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApplyAsync({
                if (it.statusCode() != 200) {
                    logger.error("http send fail requestId:{} response:{}", activeRequest.requestId, it)
                    failResponseCounter.increment()
                    return@thenApplyAsync
                }

                logger.debug("http send success requestId:{} response:{}", activeRequest.requestId, it)
                successResponseCounter.increment()
                responseScoreMarker.scoring(activeRequest, expected, it)
            }, executor)
            .exceptionally {
                logger.error("http send fail requestId:{} requestedAt:{}", activeRequest.requestId, activeRequest.requestAt, it)
                failResponseCounter.increment()
            }.thenRun {
                requestBucket.deactivateRequest(activeRequest)
            }
    }

}