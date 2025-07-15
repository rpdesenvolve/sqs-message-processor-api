package br.com.rpdesenvolve.sqs_message_processor_api.infrastructure.sqs

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageResponse

@Component
class SqsPublisherGateway(
    private val sqsClient: SqsClient,
    @Value("\${aws.sqs.queue-url}") private val queueUrl: String
) {
    private val log = LoggerFactory.getLogger(SqsPublisherGateway::class.java)

    fun sendMessage(message: String) {
        try {
            val request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build()

            val response: SendMessageResponse? = sqsClient.sendMessage(request)

            if (response == null || response.messageId() == null) {
                log.warn("Message sent, but no messageId returned")
            } else {
                log.info("Message published with ID: {}", response.messageId())
            }
        } catch (ex: Exception) {
            log.error("Failed to send message to SQS", ex)
            throw ex
        }
    }
}