package event

import common.Container
import common.Runner
import org.slf4j.LoggerFactory

class EventRunner(container: Container) : Runner {
    private val modelEventGenerator = container.modelEventGenerator
    private val modelEventPublisher = container.modelEventPublisher
    private val log = LoggerFactory.getLogger(EventRunner::class.java)

    override fun run() {
        while (true) {
            val generatedEvent = modelEventGenerator.generateEvent()
            log.debug("generated:{}", generatedEvent)

            modelEventPublisher.publish(generatedEvent)
        }
    }
}