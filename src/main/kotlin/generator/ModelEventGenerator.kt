package generator

import common.Constant
import common.randomInt
import event.*
import exception.ModelException
import model.Model
import org.slf4j.LoggerFactory

class ModelEventGenerator(
    private val model: Model,
    private val modelEvaluator: ModelEvaluator,
    private val userIdGenerator: IdGenerator,
    private val roomIdGenerator: IdGenerator,
    private val messageGenerator: IdGenerator
) {
    private val logger = LoggerFactory.getLogger(ModelEventGenerator::class.java)
    fun generateEvent(): Event {
        // while return event
        while (true) {
            val strategy = modelEvaluator.getStrategy()
            val eventType = strategy.drawEventType()
            logger.debug("strategy:$strategy eventType:$eventType")

            try {
                when (eventType) {
                    EventType.POST_CHAT -> {
                        val room = model.randomRoom()
                        return PostChatEvent(
                            roomId = room.roomId,
                            senderId = room.randomUserInvolved().userId,
                            message = messageGenerator.generate()
                        )
                    }

                    EventType.CREATE_ROOM -> {
                        val users = model.randomUser(2 + randomInt(2)).map { it.userId } // at least 2
                        return CreateRoomEvent(
                            roomId = roomIdGenerator.generate(),
                            users = users
                        )
                    }

                    EventType.DELETE_ROOM -> {
                        return DeleteRoomEvent(model.randomRoom().roomId)
                    }

                    EventType.USER_ENTRANCE -> {
                        val room = model.randomRoom()
                        val user = model.randomUser()
                        if (room.contains(user)) {
                            throw ModelException("room contains already the user")
                        }
                        return UserEntranceEvent(room.roomId, user.userId)
                    }

                    EventType.USER_SIGNUP -> {
                        return UserSignupEvent(userIdGenerator.generate())
                    }

                    EventType.USER_WITHDRAW -> {
                        return UserWithdrawEvent(model.randomUser().userId)
                    }

                    EventType.GET_ROOMS_USER_INVOLVED -> {
                        return GetRoomsUserInvolvedEvent(model.randomRoom().roomId, Constant.contentLimitSize)
                    }

                    EventType.GET_CHATS_USER_RECEIVED -> {
                        return GetChatsUserReceivedEvent(model.randomUser().userId, Constant.contentLimitSize)
                    }

                    EventType.GET_CHATS_IN_THE_ROOM -> {
                        return GetChatsInTheRoom(model.randomRoom().roomId, Constant.contentLimitSize)
                    }
                }
            } catch (e: ModelException) {
                // retry when catch exception
                logger.debug("model generation fail strategy:$strategy eventType:$eventType failMsg:${e.message}")
            } catch (e: Exception) {
                // retry when catch exception
                e.printStackTrace()
            }
        }
    }
}