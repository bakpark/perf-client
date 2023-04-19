package request

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import event.*
import generator.IdGenerator
import java.lang.RuntimeException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

class EventSubscriptionForRequest(
    val requestIdGenerator: IdGenerator,
    val responsePostProcessor: ResponsePostProcessor,
    val serverUrl: String,
    val rpsLimit: Int
) : EventSubscriber {
    val requestBucket = RequestBucket(rpsLimit)
    val httpClient = HttpClient.newHttpClient()
    val objectMapper = ObjectMapper().apply {
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    }

    override fun subscribe(event: Event) {
        requestBucket.waitForConsuming()
        val requestId = requestIdGenerator.generate()
        responsePostProcessor.register(requestId, event)

        val future: CompletableFuture<HttpResponse<String>>
        when (event) {
            is PostChatEvent -> {
                future = requestPostChat(event)
            }
            is CreateRoomEvent -> {
                future = requestPostRoom(event)
            }
            is DeleteRoomEvent -> {
                future = requestDeleteRoom(event)
            }
            is UserEntranceEvent -> {
                future = requestPostUserEntrance(event)
            }
            is UserSignupEvent -> {
                future = requestPostUserSignup(event)
            }
            is UserWithdrawEvent -> {
                future = requestDeleteUser(event)
            }
            is GetRoomsUserInvolvedEvent -> {
                future = requestGetRoomsUserInvolved(event)
            }
            is GetChatsUserReceivedEvent -> {
                future = requestGetChatsUserReceived(event)
            }
            is GetChatsInTheRoom -> {
                future = requestGetChatsInTheRoom(event)
            }
            else -> throw RuntimeException("can't reachable")
        }
        responsePostProcessor.registerFuture(requestId, future)
    }

    private fun requestPostChat(event: PostChatEvent): CompletableFuture<HttpResponse<String>> {
        return httpClient.sendAsync(
            HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(event)))
                .uri(URI.create("$serverUrl/api/chats"))
                .header("Content-Type", "application/json")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
    }

    private fun requestPostRoom(event: CreateRoomEvent): CompletableFuture<HttpResponse<String>> {
        return httpClient.sendAsync(
            HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(event)))
                .uri(URI.create("$serverUrl/api/rooms"))
                .header("Content-Type", "application/json")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
    }

    private fun requestDeleteRoom(event: DeleteRoomEvent): CompletableFuture<HttpResponse<String>> {
        return httpClient.sendAsync(
            HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("$serverUrl/api/rooms/${event.roomId}"))
                .header("Content-Type", "application/json")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
    }

    private fun requestPostUserEntrance(event: UserEntranceEvent): CompletableFuture<HttpResponse<String>> {
        return httpClient.sendAsync(
            HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(event)))
                .uri(URI.create("$serverUrl/api/entrances"))
                .header("Content-Type", "application/json")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
    }

    private fun requestPostUserSignup(event: UserSignupEvent): CompletableFuture<HttpResponse<String>> {
        return httpClient.sendAsync(
            HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(event)))
                .uri(URI.create("$serverUrl/api/users"))
                .header("Content-Type", "application/json")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
    }

    private fun requestDeleteUser(event: UserWithdrawEvent): CompletableFuture<HttpResponse<String>> {
        return httpClient.sendAsync(
            HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("$serverUrl/api/users/${event.userId}"))
                .header("Content-Type", "application/json")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
    }

    private fun requestGetRoomsUserInvolved(event: GetRoomsUserInvolvedEvent): CompletableFuture<HttpResponse<String>> {
        return httpClient.sendAsync(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("$serverUrl/api/users/${event.userId}/rooms"))
                .header("Content-Type", "application/json")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
    }

    private fun requestGetChatsUserReceived(event: GetChatsUserReceivedEvent): CompletableFuture<HttpResponse<String>> {
        return httpClient.sendAsync(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("$serverUrl/api/users/${event.userId}/chats"))
                .header("Content-Type", "application/json")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
    }

    private fun requestGetChatsInTheRoom(event: GetChatsInTheRoom): CompletableFuture<HttpResponse<String>> {
        return httpClient.sendAsync(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("$serverUrl/api/rooms/${event.roomId}/chats"))
                .header("Content-Type", "application/json")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
    }

}