package common

import org.slf4j.LoggerFactory

class SystemProperties {
    private val log = LoggerFactory.getLogger(SystemProperties::class.java)

    val userSizeLimit: Int = System.getProperty("limit.size.user").toInt()
    val roomSizeLimit: Int = System.getProperty("limit.size.room").toInt()
    val rpsLimit: Int = System.getProperty("limit.rps").toInt()
    val serverUrl: String = System.getProperty("url.server")!!
    val prometheusPushUrl: String? = System.getProperty("url.prometheus.push")

    init {
        log.info(
            "userSizeLimit:$userSizeLimit "
                    + "roomSizeLimit:$roomSizeLimit "
                    + "rpsLimit:$rpsLimit "
                    + "serverUrl:$serverUrl "
                    + "prometheusPushUrl:$prometheusPushUrl"
        )
    }
}