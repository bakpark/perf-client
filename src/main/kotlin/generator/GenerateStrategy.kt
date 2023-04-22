package generator

import common.randomInt
import event.EventType
import exception.ModelException
import kotlin.system.exitProcess

enum class GenerateStrategy(
    val rateUserSignUp: Int,
    val rateUserWithdraw: Int,
    val rateCreateRoom: Int,
    val rateDeleteRoom: Int,
    val rateUserEntrance: Int,
    val ratePostChat: Int,
    val rateGetRoomUserInvolved: Int,
    val rateGetChatsUserReceived: Int,
    val rateGetChatInTheRoom: Int
) {
    USER_INCREASE(40, 10, 15, 5, 5, 10, 5, 5, 5),
    ROOM_INCREASE(5, 5, 20, 10, 15, 30, 5, 5, 5),
    STABLE(5, 5, 5, 5, 10, 40, 10, 10, 10),
    ROOM_DECREASE(0, 0, 5, 30, 10, 40, 5, 5, 5),
    USER_DECREASE(10, 30, 0, 0, 5, 40, 5, 5, 5);

    init {
        val rateSum = rateUserSignUp + rateUserWithdraw + rateCreateRoom + rateDeleteRoom + rateUserEntrance +
                ratePostChat + rateGetRoomUserInvolved + rateGetChatsUserReceived + rateGetChatInTheRoom
        if (rateSum != GenerateStrategy.RATE_SUM) {
            println("[GenerateStrategy] invalid rate this:$this RATE_SUM:${GenerateStrategy.RATE_SUM} rateSum:${rateSum}")
            exitProcess(1)
        }
    }

    fun drawEventType(): EventType {
        var rv = randomInt(RATE_SUM)
        if (rv < rateUserSignUp) return EventType.USER_SIGNUP
        rv -= rateUserSignUp
        if (rv < rateUserWithdraw) return EventType.USER_WITHDRAW
        rv -= rateUserWithdraw
        if (rv < rateCreateRoom) return EventType.CREATE_ROOM
        rv -= rateCreateRoom

        if (rv < rateDeleteRoom) return EventType.DELETE_ROOM
        rv -= rateDeleteRoom
        if (rv < rateUserEntrance) return EventType.USER_ENTRANCE
        rv -= rateUserEntrance
        if (rv < ratePostChat) return EventType.POST_CHAT
        rv -= ratePostChat

        if (rv < rateGetRoomUserInvolved) return EventType.GET_ROOMS_USER_INVOLVED
        rv -= rateGetRoomUserInvolved
        if (rv < rateGetChatsUserReceived) return EventType.GET_CHATS_USER_RECEIVED
        rv -= rateGetChatsUserReceived
        if (rv < rateGetChatInTheRoom) return EventType.GET_CHATS_IN_THE_ROOM
        throw ModelException("event rate is invalid")
    }

    companion object {
        const val RATE_SUM = 100
    }
}