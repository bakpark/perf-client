package common

import event.ModelEventPublisher
import generator.IdGenerator
import generator.ModelEvaluator
import generator.ModelEventGenerator
import metric.ModelEventSubscriptionForMetric
import metric.MetricCollector
import model.ModelEventSubscriptionForModel
import model.Model
import request.*
import validation.ListStringGrader
import kotlin.system.exitProcess

class Container {
    val instanceId = "".addUUID(5)
    val props = SystemProperties()

    lateinit var model: Model
    lateinit var modelEvaluator: ModelEvaluator
    lateinit var modelEventGenerator: ModelEventGenerator

    lateinit var listStringGrader: ListStringGrader
    lateinit var responseScoreMarker: ResponseScoreMarker
    lateinit var requestBucket: RequestBucket
    lateinit var perfHttpClient: PerfHttpClient

    lateinit var eventSubscriptionForModel: ModelEventSubscriptionForModel
    lateinit var eventSubscriptionForRequest: ModelEventSubscriptionForRequest
    lateinit var eventSubscriptionForMetric: ModelEventSubscriptionForMetric
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

            listStringGrader = ListStringGrader()
            responseScoreMarker = ResponseScoreMarker(model, listStringGrader)
            requestBucket = RequestBucket(props.rpsLimit, createIdGenerator("req"))
            perfHttpClient = PerfHttpClient(responseScoreMarker, requestBucket, props.rpsLimit)

            eventSubscriptionForModel = ModelEventSubscriptionForModel(model)
            eventSubscriptionForRequest = ModelEventSubscriptionForRequest(props.serverUrl, perfHttpClient)
            eventSubscriptionForMetric = ModelEventSubscriptionForMetric()
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