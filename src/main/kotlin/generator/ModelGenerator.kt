package generator

import event.publish.ModelEventPublisher
import model.Model

class ModelGenerator(
    val model: Model,
    val modelEvaluator: ModelEvaluator,
    val modelEventPublisher: ModelEventPublisher,
    val userIdGenerator: IdGenerator,
    val roomIdGenerator: IdGenerator,
    val messageGenerator: IdGenerator
) {

}