package model

data class Chat(
    val senderId: String,
    val roomId: String,
    val message: String
)