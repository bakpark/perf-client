package metric

import event.*
import io.micrometer.core.instrument.Counter

class EventSubscriptionForMetric : EventSubscriber {
    private var postChatCounter: Counter = Counter.builder("post_chat_event_counter")
        .register(MetricCollector.registry)
    private var createRoomCounter: Counter = Counter.builder("create_room_event_counter")
        .register(MetricCollector.registry)
    private var deleteRoomCounter: Counter = Counter.builder("delete_room_event_counter")
        .register(MetricCollector.registry)


    private var userEntranceCounter: Counter = Counter.builder("user_entrance_event_counter")
        .register(MetricCollector.registry)

    private var userSignUpCounter: Counter = Counter.builder("user_signup_event_counter")
        .register(MetricCollector.registry)

    private var userWithdrawCounter: Counter = Counter.builder("user_withdraw_event_counter")
        .register(MetricCollector.registry)


    private var getRoomUserInvolvedCounter: Counter = Counter.builder("get_room_user_involved_event_counter")
        .register(MetricCollector.registry)

    private var getChatsUserReceivedCounter: Counter = Counter.builder("get_chats_user_received_event_counter")
        .register(MetricCollector.registry)

    private var getChatsInTheRoomCounter: Counter = Counter.builder("get_chats_user_received_event_counter")
        .register(MetricCollector.registry)
    override fun subscribe(event: Event) {
        when (event) {
        }
    }
}