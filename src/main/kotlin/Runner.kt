import event.ChatEventPublisher
import event.RoomEventPublisher
import event.UserEventPublisher
import event.VerifyActionProducer
import model.Model
import kotlin.system.exitProcess

class Runner(
    private val runnerId: String = "".addUUID(5)
) {

    private var userIdGenerator: IdGenerator
    private var roomIdGenerator: IdGenerator
    private var messageGenerator: IdGenerator

    private var model: Model
    private var chatEventPublisher: ChatEventPublisher
    private var roomEventPublisher: RoomEventPublisher
    private var userEventPublisher: UserEventPublisher
    private var verifyActionProducer: VerifyActionProducer

    init {
        try {
            val userLimit: Int = System.getProperty("limit.user").toInt()
            val roomLimit: Int = System.getProperty("limit.room").toInt()
            val requestPerSec: Int = System.getProperty("request.per.second").toInt()

            println("runnerId:${runnerId} userLimit:$userLimit roomLimit:$roomLimit requestPerSec:$requestPerSec")

            userIdGenerator = IdGenerator("$runnerId-user-")
            roomIdGenerator = IdGenerator("$runnerId-user-")
            messageGenerator = IdGenerator("$runnerId-msg-")
            model = Model(userIdGenerator, roomIdGenerator)
            chatEventPublisher = ChatEventPublisher(model)
            roomEventPublisher = RoomEventPublisher(model, roomLimit)
            userEventPublisher = UserEventPublisher(model, userLimit)
            verifyActionProducer = VerifyActionProducer(model)

        } catch (e: Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
    }
}