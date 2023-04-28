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

class PerfHttpClient(
    private val responseScoreMarker: ResponseScoreMarker,
    private val requestBucket: RequestBucket,
    private val rpsLimit: Int
) {
    private val logger = LoggerFactory.getLogger(PerfHttpClient::class.java)
    private val executor: Executor = object : ThreadPoolExecutor(
        10,
        20,
        0,
        TimeUnit.SECONDS,
        ArrayBlockingQueue(100)
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
    fun sendAsync(request: HttpRequest.Builder, event: Event) {
        val activeRequest = requestBucket.activateRequest(event.type())
        val expected = responseScoreMarker.getExpectedResult(event)
        requestCounter.increment()

        httpClient.sendAsync(
            request
                .header("Content-Type", "application/json")
                .header("Request-Id", activeRequest.requestId)
                .timeout(Duration.ofSeconds(10L))
                .build(),
            HttpResponse.BodyHandlers.ofString()
        ).thenApplyAsync({
            if (it.statusCode() != 200) {
                logger.error("fail requestId:{} response:{}", activeRequest.requestId, it)
                failResponseCounter.increment()
                return@thenApplyAsync
            }

            logger.debug(
                "success requestId:{} response:{} requestedAt:{}", activeRequest.requestId, it, activeRequest.requestAt
            )
            successResponseCounter.increment()
            responseScoreMarker.scoring(activeRequest, expected, it)
        }, executor)
            .exceptionally {
                logger.error("fail requestId:{} requestedAt:{}", activeRequest.requestId, activeRequest.requestAt, it)
                failResponseCounter.increment()
            }.thenRun {
                requestBucket.deactivateRequest(activeRequest)
            }
    }

}