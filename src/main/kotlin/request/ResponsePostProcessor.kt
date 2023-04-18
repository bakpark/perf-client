package request

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import event.Event
import model.Model
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

class ResponsePostProcessor(
    model: Model
) {
    val objectMapper = ObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    fun registerEvent(requestId: String, event: Event) {
    }

    fun registerFuture(requestId: String, future: CompletableFuture<HttpResponse<String>>) {
    }

}
