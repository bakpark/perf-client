package request

import event.Event
import event.EventSubscriber
import java.net.http.HttpClient

class EventSubscriptionForRequest(
    val serverUrl: String,
    rps: Int
): EventSubscriber {
    val requestBucket = RequestBucket(rps)
    val httpClient = HttpClient.newHttpClient()
    override fun subscribe(event: Event) {
        // todo
    }
}