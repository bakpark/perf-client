package event

import generator.GenerateStrategy
import org.junit.jupiter.api.Test

class EventTypeTest {
    @Test
    fun drawEventTest() {
        repeat(10000) {
            GenerateStrategy.STABLE.drawEventType()
            GenerateStrategy.USER_DECREASE.drawEventType()
            GenerateStrategy.ROOM_DECREASE.drawEventType()
            GenerateStrategy.ROOM_INCREASE.drawEventType()
            GenerateStrategy.USER_INCREASE.drawEventType()
        }
    }
}