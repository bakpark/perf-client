package request

import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

class FutureResponse(
    val requestId: String,
    val future: CompletableFuture<HttpResponse<String>>
) {

}
