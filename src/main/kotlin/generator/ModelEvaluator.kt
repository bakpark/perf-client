package generator

import io.micrometer.core.instrument.Gauge
import metric.MetricCollector
import model.Model
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class ModelEvaluator(
    val model: Model,
    private val userLimit: Int,
    private val roomLimit: Int
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    private var lastCalled = LocalDateTime.now()

    init {
        Gauge.builder("user_count", model.users::size)
            .description("The number of the users")
            .register(MetricCollector.registry)
        Gauge.builder("room_count", model.rooms::size)
            .description("The number of the room")
            .register(MetricCollector.registry)
        Gauge.builder("random_room_size", model::randomRoomSize)
            .description("random room's size")
            .register(MetricCollector.registry)
    }

    fun getStrategy(): GenerateStrategy {
        if (lastCalled.isBefore(LocalDateTime.now().minusSeconds(10L))) {
            log.info("user: {}({}) room: {}({})", model.users.size, userLimit, model.rooms.size, roomLimit)
            lastCalled = LocalDateTime.now()
        }
        if (model.users.size * 3 < userLimit) {
            return GenerateStrategy.USER_INCREASE
        }
        if (model.rooms.size * 3 < roomLimit) {
            return GenerateStrategy.ROOM_INCREASE
        }
        if (model.rooms.size * 4 > roomLimit) {
            return GenerateStrategy.ROOM_DECREASE
        }
        if (model.users.size * 4 > userLimit) {
            return GenerateStrategy.USER_DECREASE
        }
        return GenerateStrategy.STABLE
    }

}