package generator

import model.Model

class ModelEvaluator(
    val model: Model,
    private val userLimit: Int,
    private val roomLimit: Int
) {
    fun getStrategy(): GenerateStrategy {
        if (model.users.size * 3 < userLimit) {
            return GenerateStrategy.USER_INCREASE
        }
        if (model.rooms.size * 3 < roomLimit) {
            return GenerateStrategy.ROOM_INCREASE
        }
        if (model.rooms.size * 4 > roomLimit) {
            return GenerateStrategy.ROOM_DECREASE
        }
        if (model.users.size * 4 > userLimit) {
            return GenerateStrategy.USER_DECREASE
        }
        return GenerateStrategy.STABLE
    }

}