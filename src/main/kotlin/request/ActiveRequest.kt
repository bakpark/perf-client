package request

import event.EventType
import java.time.LocalDateTime

class ActiveRequest(
    val requestId: String,
    val eventType: EventType,
    val requestAt: LocalDateTime
)