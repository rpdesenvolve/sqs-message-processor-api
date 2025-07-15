package br.com.rpdesenvolve.sqs_message_processor_api.application.usecase

import br.com.rpdesenvolve.sqs_message_processor_api.common.MessageConverter
import br.com.rpdesenvolve.sqs_message_processor_api.domain.model.MessagePayload
import br.com.rpdesenvolve.sqs_message_processor_api.infrastructure.sqs.SqsPublisherGateway
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class PublishMessageUseCase(
    private val sqsPublisherGateway: SqsPublisherGateway,
    private val messageConverter: MessageConverter
) {
    private val log = LoggerFactory.getLogger(PublishMessageUseCase::class.java)

    fun execute(payload: String) {
        val messagePayload = MessagePayload(
            id = UUID.randomUUID().toString(),
            content = payload,
            timestamp = Instant.now().toEpochMilli()
        )

        val message = messageConverter.serialize(messagePayload)

        log.info("Publishing message to SQS: {}", message)
        sqsPublisherGateway.sendMessage(message)
    }
}