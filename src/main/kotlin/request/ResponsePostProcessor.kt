package request

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import event.*
import exception.ModelException
import io.micrometer.core.instrument.Counter
import metric.MetricCollector
import model.Model
import org.slf4j.LoggerFactory
import response.ChatsResponse
import response.RoomsResponse
import validation.ListValidator
import java.net.http.HttpResponse

class ResponsePostProcessor(
    private val model: Model,
    private val listValidator: ListValidator
) {
    private val objectMapper = ObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
    private val registered = HashMap<String, EventType>()

    private val scoreCounter = Counter.builder("validation_score")
        .register(MetricCollector.registry)
    private val failResponseCounter = Counter.builder("response_fail_count")
        .register(MetricCollector.registry)

    fun register(requestId: String, event: Event) {
        when (event) {
            is GetRoomsUserInvolvedEvent -> {
                val expected = model.users[event.userId]?.rooms?.map { it.roomId }
                    ?: throw ModelException("userId not found")
                registered[requestId] = EventType.GET_ROOMS_USER_INVOLVED
                listValidator.register(requestId, expected)
            }

            is GetChatsUserReceivedEvent -> {
                val expected = model.users[event.userId]?.getChats()?.map { it.message }
                    ?: throw ModelException("userId not found")
                registered[requestId] = EventType.GET_CHATS_USER_RECEIVED
                listValidator.register(requestId, expected)
            }

            is GetChatsInTheRoom -> {
                val expected = model.rooms[event.roomId]?.getChats()?.map { it.message }
                    ?: throw ModelException("roomId not found")
                registered[requestId] = EventType.GET_CHATS_IN_THE_ROOM
                listValidator.register(requestId, expected)
            }
        }
    }

    fun deregister(requestId: String) {
        registered.remove(requestId)
        listValidator.deregister(requestId)
    }

    fun processResponse(requestId: String, response: HttpResponse<String>) {
        if (response.statusCode() != 200 || registered[requestId] == null) {
            failResponseCounter.increment()
            return
        }

        val actual = when (registered.remove(requestId)) {
            EventType.GET_ROOMS_USER_INVOLVED -> {
                val roomsResponse = objectMapper.convertValue(response.body(), RoomsResponse::class.java)
                roomsResponse.rooms
            }
            EventType.GET_CHATS_USER_RECEIVED -> {
                val chatsResponse = objectMapper.convertValue(response.body(), ChatsResponse::class.java)
                chatsResponse.chats
            }
            EventType.GET_CHATS_IN_THE_ROOM -> {
                val chatsResponse = objectMapper.convertValue(response.body(), ChatsResponse::class.java)
                chatsResponse.chats
            }
            else -> emptyList()
        }

        val score = listValidator.validate(requestId, actual)
        scoreCounter.increment(score)
    }

}
