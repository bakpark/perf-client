package metric

import event.*
import io.micrometer.core.instrument.Counter

class EventSubscriptionForMetric : EventSubscriber {
    private var postChatCounter = createCounter("post_chat")
    private var createRoomCounter = createCounter("create_room")
    private var deleteRoomCounter = createCounter("delete_room")

    private var userEntranceCounter = createCounter("user_entrance")
    private var userSignUpCounter = createCounter("user_signup")
    private var userWithdrawCounter = createCounter("user_withdraw")

    private var getRoomUserInvolvedCounter = createCounter("get_rooms_user_involved")
    private var getChatsUserReceivedCounter = createCounter("get_chats_user_received")
    private var getChatsInTheRoomCounter = createCounter("get_chats_in_the_room")

    override fun subscribe(event: Event) {
        when (event) {
            is PostChatEvent -> postChatCounter.increment()
            is CreateRoomEvent -> createRoomCounter.increment()
            is DeleteRoomEvent -> deleteRoomCounter.increment()
            is UserEntranceEvent -> userEntranceCounter.increment()
            is UserSignupEvent -> userSignUpCounter.increment()
            is UserWithdrawEvent -> userWithdrawCounter.increment()
            is GetRoomsUserInvolvedEvent -> getRoomUserInvolvedCounter.increment()
            is GetChatsUserReceivedEvent -> getChatsUserReceivedCounter.increment()
            is GetChatsInTheRoom -> getChatsInTheRoomCounter.increment()
        }
    }

    private fun createCounter(typeTag: String) = Counter.builder("event_count")
        .tag("type", typeTag)
        .register(MetricCollector.registry)
}