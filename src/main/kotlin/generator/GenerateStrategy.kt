package generator

enum class GenerateStrategy(
    val rateUserSignUp: Int,
    val rateUserWithdraw: Int,
    val rateCreateRoom: Int,
    val rateDeleteRoom: Int,
    val rateUserEntrance: Int,
    val ratePostChat: Int,
    val rateGetUserInvolvedRoom: Int,
    val rateGetChatsUserReceived: Int,
    val rateGetChatInTheRoom: Int
) {
    USER_INCREASE(65,0,5,0,5,10,5,5,5),
    ROOM_INCREASE(0,0,20,10,20,35,5,5,5),
    STABLE(10,10,10,5,10,40,5,5,5),
    ROOM_DECREASE(5,5,5,20,10,40,5,5,5),
    USER_DECREASE(5,20,5,10,5,40,5,5,5);
}