package event

import model.Model

class UserEventPublisher(
    val model: Model,
    val userLimit: Int
) {
    fun publishUserSignupEvent(): UserSignupEvent {
        return UserSignupEvent("")
    }

    fun publishUserWithdrawEvent(): UserWithdrawEvent {
        return UserWithdrawEvent("")
    }
}