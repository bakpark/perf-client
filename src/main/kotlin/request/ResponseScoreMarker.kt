package request

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import event.*
import exception.ModelException
import io.micrometer.core.instrument.Counter
import metric.MetricCollector
import model.Model
import response.ChatsResponse
import response.RoomsResponse
import validation.ListStringGrader
import java.net.http.HttpResponse

class ResponseScoreMarker(
    private val model: Model,
    private val listStringGrader: ListStringGrader
) {
    private val objectMapper = jacksonObjectMapper()

    private val scoreCounter = Counter.builder("validation_score")
        .register(MetricCollector.registry)

    fun getExpectedResult(event: Event): List<String> {
        return when (event) {
            is GetRoomsUserInvolvedEvent -> {
                model.users[event.userId]?.rooms?.map { it.roomId }
                    ?: throw ModelException("userId not found")
            }

            is GetChatsUserReceivedEvent -> {
                model.users[event.userId]?.getChats()?.map { it.message }
                    ?: throw ModelException("userId not found")
            }

            is GetChatsInTheRoom -> {
                model.rooms[event.roomId]?.getChats()?.map { it.message }
                    ?: throw ModelException("roomId not found")
            }

            else -> emptyList()
        }
    }

    fun scoring(activeRequest: ActiveRequest, expected: List<String>, response: HttpResponse<String>) {
        val actual = when (activeRequest.eventType) {
            EventType.GET_ROOMS_USER_INVOLVED -> {
                val roomsResponse = objectMapper.readValue(response.body(), RoomsResponse::class.java)
                roomsResponse.rooms
            }

            EventType.GET_CHATS_USER_RECEIVED -> {
                val chatsResponse = objectMapper.readValue(response.body(), ChatsResponse::class.java)
                chatsResponse.chats
            }

            EventType.GET_CHATS_IN_THE_ROOM -> {
                val chatsResponse = objectMapper.readValue(response.body(), ChatsResponse::class.java)
                chatsResponse.chats
            }

            else -> emptyList()
        }

        val score = listStringGrader.scoring(expected, actual)
        scoreCounter.increment(score)
    }
}
