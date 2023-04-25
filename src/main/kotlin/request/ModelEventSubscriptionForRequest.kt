package request

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import event.*
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers

class ModelEventSubscriptionForRequest(
    private val serverUrl: String,
    private val httpClient: PerfHttpClient
) : ModelEventSubscriber {

    private val objectMapper = jacksonObjectMapper()

    override fun subscribe(event: Event) {
        when (event) {
            is PostChatEvent -> requestPostChat(event)
            is CreateRoomEvent -> requestPostRoom(event)
            is DeleteRoomEvent -> requestDeleteRoom(event)
            is UserEntranceEvent -> requestPostUserEntrance(event)
            is UserSignupEvent -> requestPostUserSignup(event)
            is UserWithdrawEvent -> requestDeleteUser(event)
            is GetRoomsUserInvolvedEvent -> requestGetRoomsUserInvolved(event)
            is GetChatsUserReceivedEvent -> requestGetChatsUserReceived(event)
            is GetChatsInTheRoom -> requestGetChatsInTheRoom(event)

            else -> throw RuntimeException("can't reachable")
        }
    }

    private fun requestPostChat(event: PostChatEvent) {
        httpClient.sendAsync(
            HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(event)))
                .uri(URI.create("$serverUrl/api/chats"))
                .header("Content-Type", "application/json")
                .build(),
            event
        )
    }

    private fun requestPostRoom(event: CreateRoomEvent) {
        val str = objectMapper.writeValueAsString(event)
        httpClient.sendAsync(
            HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(str))
                .uri(URI.create("$serverUrl/api/rooms"))
                .header("Content-Type", "application/json")
                .build(),
            event
        )
    }

    private fun requestDeleteRoom(event: DeleteRoomEvent) {
        httpClient.sendAsync(
            HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("$serverUrl/api/rooms/${event.roomId}"))
                .header("Content-Type", "application/json")
                .build(),
            event
        )
    }

    private fun requestPostUserEntrance(event: UserEntranceEvent) {
        httpClient.sendAsync(
            HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(event)))
                .uri(URI.create("$serverUrl/api/entrances"))
                .header("Content-Type", "application/json")
                .build(),
            event
        )
    }

    private fun requestPostUserSignup(event: UserSignupEvent) {
        httpClient.sendAsync(
            HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(event)))
                .uri(URI.create("$serverUrl/api/users"))
                .header("Content-Type", "application/json")
                .build(),
            event
        )
    }

    private fun requestDeleteUser(event: UserWithdrawEvent) {
        httpClient.sendAsync(
            HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("$serverUrl/api/users/${event.userId}"))
                .header("Content-Type", "application/json")
                .build(),
            event
        )
    }

    private fun requestGetRoomsUserInvolved(event: GetRoomsUserInvolvedEvent) {
        httpClient.sendAsync(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("$serverUrl/api/users/${event.userId}/rooms?size=${event.limit}"))
                .header("Content-Type", "application/json")
                .build(),
            event
        )
    }

    private fun requestGetChatsUserReceived(event: GetChatsUserReceivedEvent) {
        httpClient.sendAsync(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("$serverUrl/api/users/${event.userId}/chats?size=${event.limit}"))
                .header("Content-Type", "application/json")
                .build(),
            event
        )
    }

    private fun requestGetChatsInTheRoom(event: GetChatsInTheRoom) {
        httpClient.sendAsync(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("$serverUrl/api/rooms/${event.roomId}/chats?size=${event.limit}"))
                .header("Content-Type", "application/json")
                .build(),
            event
        )
    }

}