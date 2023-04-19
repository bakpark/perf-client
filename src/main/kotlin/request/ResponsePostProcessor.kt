package request

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import event.Event
import event.GetChatsInTheRoom
import event.GetChatsUserReceivedEvent
import event.GetRoomsUserInvolvedEvent
import exception.ModelException
import model.Model
import org.slf4j.LoggerFactory
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

class ResponsePostProcessor(
    val model: Model,
    val responseValidator: ResponseValidator
) {
    val objectMapper = ObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
    val log = LoggerFactory.getLogger(this::class.java)

    fun register(requestId: String, event: Event) {
        when (event) {
            is GetRoomsUserInvolvedEvent -> {
                val expected = model.users[event.userId]?.rooms?.map { it.roomId }
                    ?: throw ModelException("userId not found")
                responseValidator.register(requestId, expected)
            }

            is GetChatsUserReceivedEvent -> {
                val expected = model.users[event.userId]?.getChats()?.map { it.message }
                    ?: throw ModelException("userId not found")
                responseValidator.register(requestId, expected)
            }

            is GetChatsInTheRoom -> {
                val expected = model.rooms[event.roomId]?.getChats()?.map { it.message }
                    ?: throw ModelException("roomId not found")
                responseValidator.register(requestId, expected)
            }
        }
    }

    fun registerFuture(requestId: String, future: CompletableFuture<HttpResponse<String>>) {
        future.thenAcceptAsync {
            log.info("requestId:$requestId response:$it")
        }
    }

}
