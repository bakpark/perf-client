import common.addUUID
import event.publish.ModelEventPublisher
import event.subscribe.ModelEventSubscription
import event.subscribe.RequestEventSubscription
import generator.IdGenerator
import generator.ModelEvaluator
import generator.ModelEventGenerator
import model.Model
import org.slf4j.LoggerFactory
import kotlin.system.exitProcess

class Runner(
    runnerId: String = "".addUUID(5)
) {
    private val logger = LoggerFactory.getLogger(Runner::class.java)

    private var userIdGenerator: IdGenerator
    private var roomIdGenerator: IdGenerator
    private var messageGenerator: IdGenerator

    private var model: Model
    private var modelEvaluator: ModelEvaluator
    private var modelEventGenerator: ModelEventGenerator

    private var modelEventSubscription: ModelEventSubscription
    private var requestEventSubscription: RequestEventSubscription
    private var modelEventPublisher: ModelEventPublisher


    init {
        try {
            val userLimit: Int = System.getProperty("limit.user").toInt()
            val roomLimit: Int = System.getProperty("limit.room").toInt()
            val requestPerSec: Int = System.getProperty("request.per_second").toInt()
            val baseUrl: String = System.getProperty("request.base_url")!!

            logger.info("runnerId:${runnerId} userLimit:$userLimit roomLimit:$roomLimit requestPerSec:$requestPerSec baseUrl:$baseUrl")

            userIdGenerator = IdGenerator("$runnerId-user-")
            roomIdGenerator = IdGenerator("$runnerId-user-")
            messageGenerator = IdGenerator("$runnerId-msg-")

            model = Model(userIdGenerator, roomIdGenerator)
            modelEvaluator = ModelEvaluator(model, userLimit, roomLimit)
            modelEventGenerator =
                ModelEventGenerator(model, modelEvaluator, userIdGenerator, roomIdGenerator, messageGenerator)

            modelEventSubscription = ModelEventSubscription(model)
            requestEventSubscription = RequestEventSubscription(baseUrl, requestPerSec)
            modelEventPublisher = ModelEventPublisher(modelEventSubscription, requestEventSubscription)

        } catch (e: Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
    }

    fun run() {
        val generated = modelEventGenerator.generateEvent()
        logger.debug("generated:{}", generated)
        modelEventPublisher.publish(generated)
    }
}