import generator.IdGenerator
import generator.ModelGenerator
import model.Model
import kotlin.system.exitProcess

class Runner(
    runnerId: String = "".addUUID(5)
) {

    private var userIdGenerator: IdGenerator
    private var roomIdGenerator: IdGenerator
    private var messageGenerator: IdGenerator

    private var model: Model
    private var modelGenerator: ModelGenerator


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
            modelGenerator = ModelGenerator(model, userIdGenerator, roomIdGenerator, messageGenerator)


        } catch (e: Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
    }
}