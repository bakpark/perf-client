package request

import event.Event
import common.RequestBucket
import event.EventSubscriber
import java.net.http.HttpClient

class EventSubscriptionForRequest(
    val baseUrl: String,
    requestPerSeconds: Int
): EventSubscriber {
    val requestBucket = RequestBucket(requestPerSeconds)
    val httpClient = HttpClient.newHttpClient()
    override fun subscribe(event: Event) {
        // todo
    }
}