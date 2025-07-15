package br.com.rpdesenvolve.sqs_message_processor_api.unit.controller

import br.com.rpdesenvolve.sqs_message_processor_api.adapter.inbound.controller.MessageController
import br.com.rpdesenvolve.sqs_message_processor_api.application.usecase.PublishMessageUseCase
import br.com.rpdesenvolve.sqs_message_processor_api.common.dto.MessageRequestDTO
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.Test

class MessageControllerTest {

    private lateinit var useCase: PublishMessageUseCase
    private lateinit var controller: MessageController

    @BeforeEach
    fun setup() {
        useCase = mock()
        controller = MessageController(useCase)
    }

    @Test
    fun `should return 200 when posting message`() {
        val dto = MessageRequestDTO(payload = "Test message for SQS queue")

        val response: ResponseEntity<String> = controller.publishMessage(dto)

        assert(response.statusCode == HttpStatus.OK)
        verify(useCase).execute(dto.payload)
    }
}