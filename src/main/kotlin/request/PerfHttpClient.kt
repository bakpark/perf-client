package request

import event.Event
import org.slf4j.LoggerFactory
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class PerfHttpClient(
    val responsePostProcessor: ResponsePostProcessor,
    val requestBucket: RequestBucket,
    val rpsLimit: Int
) {
    val log = LoggerFactory.getLogger(PerfHttpClient::class.java)
    val executor: Executor = object : ThreadPoolExecutor(
        10,
        rpsLimit / 10,
        0,
        TimeUnit.SECONDS,
        ArrayBlockingQueue(rpsLimit * 2)
    ) {}

    val httpClient: HttpClient = HttpClient.newHttpClient()

    fun sendAsync(request: HttpRequest, event: Event) {
        val requestId = requestBucket.drawRequestId()

        responsePostProcessor.register(requestId, event)

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApplyAsync({ responsePostProcessor.processResponse(requestId, it) }, executor)
    }

}