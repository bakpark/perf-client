package action

class UserActionProducer(val userLimit: Int) {
    fun userSignupAction(): UserSignupAction {
        return UserSignupAction("")
    }

    fun userWithdrawAction(): UserWithdrawAction {
        return UserWithdrawAction("")
    }
}