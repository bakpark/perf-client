package generator

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GenerateStrategyTest {

    @Test
    fun rateSum() {
        GenerateStrategy.values().forEach {
            val sum: Int = it.rateCreateRoom +
                    it.rateDeleteRoom +
                    it.ratePostChat +
                    it.rateUserSignUp +
                    it.rateUserEntrance +
                    it.rateGetChatInTheRoom +
                    it.rateGetChatsUserReceived +
                    it.rateGetRoomUserInvolved +
                    it.rateUserWithdraw
            Assertions.assertEquals(sum, GenerateStrategy.RATE_SUM)
        }
    }
}