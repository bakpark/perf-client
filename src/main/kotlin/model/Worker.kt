package model

import kotlin.system.exitProcess

class Worker {
    private var producer: ActionProducer

    init {
        try {
            val userLimit: Int = System.getProperty("limit.user").toInt()
            val roomLimit: Int = System.getProperty("limit.room").toInt()
            val requestPerSec: Int = System.getProperty("action.per.second").toInt()

            println("userLimit:$userLimit roomLimit:$roomLimit requestPerSec:$requestPerSec")
            producer = ActionProducer(userLimit, roomLimit)
        } catch (e: Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
    }
}