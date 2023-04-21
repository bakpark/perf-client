package common

import event.ModelEventPublisher
import generator.IdGenerator
import generator.ModelEvaluator
import generator.ModelEventGenerator
import metric.EventSubscriptionForMetric
import metric.MetricCollector
import model.EventSubscriptionForModel
import model.Model
import request.*
import kotlin.system.exitProcess

class Container {
    val instanceId = "".addUUID(5)
    val props = SystemProperties()

    lateinit var model: Model
    lateinit var modelEvaluator: ModelEvaluator
    lateinit var modelEventGenerator: ModelEventGenerator

    lateinit var responseValidator: ResponseValidator
    lateinit var responsePostProcessor: ResponsePostProcessor
    lateinit var requestBucket: RequestBucket
    lateinit var perfHttpClient: PerfHttpClient

    lateinit var eventSubscriptionForModel: EventSubscriptionForModel
    lateinit var eventSubscriptionForRequest: EventSubscriptionForRequest
    lateinit var eventSubscriptionForMetric: EventSubscriptionForMetric
    lateinit var modelEventPublisher: ModelEventPublisher

    lateinit var metricCollector: MetricCollector


    fun init() {
        try {
            model = Model(
                userIdGenerator = createIdGenerator("user"),
                roomIdGenerator = createIdGenerator("room")
            )
            modelEvaluator = ModelEvaluator(model, props.userSizeLimit, props.roomSizeLimit)
            modelEventGenerator = ModelEventGenerator(
                model = model,
                modelEvaluator = modelEvaluator,
                userIdGenerator = createIdGenerator("user"),
                roomIdGenerator = createIdGenerator("room"),
                messageGenerator = createIdGenerator("msg")
            )

            responseValidator = ResponseValidator()
            responsePostProcessor = ResponsePostProcessor(model, responseValidator)
            requestBucket = RequestBucket(props.rpsLimit, createIdGenerator("req"))
            perfHttpClient = PerfHttpClient(responsePostProcessor, requestBucket, props.rpsLimit)

            eventSubscriptionForModel = EventSubscriptionForModel(model)
            eventSubscriptionForRequest = EventSubscriptionForRequest(props.serverUrl, perfHttpClient)
            eventSubscriptionForMetric = EventSubscriptionForMetric()
            modelEventPublisher = ModelEventPublisher(
                eventSubscriptionForModel = eventSubscriptionForModel,
                eventSubscriptionForRequest = eventSubscriptionForRequest,
                eventSubscriptionForMetric = eventSubscriptionForMetric
            )

            metricCollector = MetricCollector.create(props.prometheusPushUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            exitProcess(1)
        }
    }


    fun createIdGenerator(name: String): IdGenerator = IdGenerator("$instanceId-$name-")
}