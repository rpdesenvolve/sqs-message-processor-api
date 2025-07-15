package br.com.rpdesenvolve.sqs_message_processor_api.integration

import br.com.rpdesenvolve.sqs_message_processor_api.common.dto.MessageRequestDTO
import br.com.rpdesenvolve.sqs_message_processor_api.config.mock.AwsMockBeans
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageResponse
import java.util.UUID
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(AwsMockBeans::class)
class MessageControllerIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var sqsClient: SqsClient

    @BeforeEach
    fun setup() {
        `when`(sqsClient.sendMessage(any(SendMessageRequest::class.java)))
            .thenReturn(
                SendMessageResponse.builder()
                    .messageId(UUID.randomUUID().toString())
                    .build()
            )
    }

    @Test
    fun `should post message to controller and return OK`() {
        val url = "http://localhost:$port/api/v1/messages"

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val request = MessageRequestDTO("This is a test message sent to the SQS queue through the MessageController.")
        val entity = HttpEntity(request, headers)

        val response = restTemplate.postForEntity(url, entity, String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }
}