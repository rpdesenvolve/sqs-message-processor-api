package br.com.rpdesenvolve.sqs_message_processor_api.unit.sqs

import br.com.rpdesenvolve.sqs_message_processor_api.infrastructure.sqs.SqsPublisherGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageResponse
import java.util.UUID
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class SqsPublisherGatewayTest {

    @Mock
    lateinit var sqsClient: SqsClient

    lateinit var sqsPublisherGateway: SqsPublisherGateway

    private val queueUrl = "https://dummy-queue-url"

    @BeforeEach
    fun setup() {
        sqsPublisherGateway = SqsPublisherGateway(sqsClient, queueUrl)
    }

    @Test
    fun `should send message successfully`() {
        `when`(sqsClient.sendMessage(any(SendMessageRequest::class.java)))
            .thenReturn(
                SendMessageResponse.builder()
                    .messageId(UUID.randomUUID().toString())
                    .build()
            )

        val message = "Test message"
        sqsPublisherGateway.sendMessage(message)

        verify(sqsClient).sendMessage(any(SendMessageRequest::class.java))
    }

    @Test
    fun `should throw exception on SQS failure`() {
        `when`(sqsClient.sendMessage(any(SendMessageRequest::class.java)))
            .thenThrow(RuntimeException("SQS failure"))

        val exception = assertThrows<RuntimeException> {
            sqsPublisherGateway.sendMessage("fail message")
        }

        assert(exception.message == "SQS failure")
    }
}