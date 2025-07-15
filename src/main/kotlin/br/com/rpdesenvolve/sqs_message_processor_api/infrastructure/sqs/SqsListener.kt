package br.com.rpdesenvolve.sqs_message_processor_api.infrastructure.sqs

import br.com.rpdesenvolve.sqs_message_processor_api.application.usecase.ProcessMessageUseCase
import br.com.rpdesenvolve.sqs_message_processor_api.common.MessageConverter
import br.com.rpdesenvolve.sqs_message_processor_api.domain.model.MessagePayload
import org.slf4j.LoggerFactory
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class SqsListener(
    private val processMessageUseCase: ProcessMessageUseCase,
    private val messageConverter: MessageConverter
) {
    private val log = LoggerFactory.getLogger(SqsListener::class.java)

    @SqsListener("\${aws.sqs.queue-name}")
    fun receiveMessage(message: String) {
        try {
            val payload = messageConverter.deserialize(message, MessagePayload::class.java)

            log.info("Message received: {}", payload)

            processMessageUseCase.execute(payload)
        } catch (ex: Exception) {
            log.error("Error processing message", ex)
            throw ex
        }
    }
}