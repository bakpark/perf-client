package event.subscribe

import event.Event
import generator.RequestBucket
import java.net.http.HttpClient

class RequestEventSubscription(
    val baseUrl: String,
    requestPerSeconds: Int
):EventSubscriber {
    val requestBucket = RequestBucket(requestPerSeconds)
    val httpClient = HttpClient.newHttpClient()
    override fun subscribe(event: Event) {
        TODO("Not yet implemented")
    }
}