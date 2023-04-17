package metric

import event.*
import io.micrometer.core.instrument.Counter

class EventSubscriptionForMetric : EventSubscriber {
    private var postChatCounter: Counter = Counter.builder("event_count")
        .tag("type", "post_chat")
        .register(MetricCollector.registry)
    private var createRoomCounter: Counter = Counter.builder("event_count")
        .tag("type", "create_room")
        .register(MetricCollector.registry)
    private var deleteRoomCounter: Counter = Counter.builder("event_count")
        .tag("type", "delete_room")
        .register(MetricCollector.registry)

    private var userEntranceCounter: Counter = Counter.builder("event_count")
        .tag("type", "user_entrance")
        .register(MetricCollector.registry)
    private var userSignUpCounter: Counter = Counter.builder("event_count")
        .tag("type", "user_signup")
        .register(MetricCollector.registry)
    private var userWithdrawCounter: Counter = Counter.builder("event_count")
        .tag("type", "user_withdraw")
        .register(MetricCollector.registry)

    private var getRoomUserInvolvedCounter: Counter = Counter.builder("event_count")
        .tag("type", "get_rooms_user_involved")
        .register(MetricCollector.registry)
    private var getChatsUserReceivedCounter: Counter = Counter.builder("event_count")
        .tag("type", "get_chats_user_received")
        .register(MetricCollector.registry)
    private var getChatsInTheRoomCounter: Counter = Counter.builder("event_count")
        .tag("type", "get_chats_in_the_room")
        .register(MetricCollector.registry)

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
}