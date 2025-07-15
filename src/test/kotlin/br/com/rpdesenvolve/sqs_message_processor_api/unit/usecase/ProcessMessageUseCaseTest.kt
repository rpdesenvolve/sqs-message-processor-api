package br.com.rpdesenvolve.sqs_message_processor_api.unit.usecase

import br.com.rpdesenvolve.sqs_message_processor_api.application.usecase.ProcessMessageUseCase
import br.com.rpdesenvolve.sqs_message_processor_api.domain.model.MessagePayload
import java.time.Instant
import java.util.UUID
import kotlin.test.Test

class ProcessMessageUseCaseTest {

    private val useCase = ProcessMessageUseCase()

    @Test
    fun `must process message with valid content`() {
        val payload = MessagePayload(
            id = UUID.randomUUID().toString(),
            content = "Valid message content",
            timestamp = Instant.now().toEpochMilli()
        )

        useCase.execute(payload)
    }

    @Test
    fun `should ignore message with empty content`() {
        val payload = MessagePayload(
            id = UUID.randomUUID().toString(),
            content = "",
            timestamp = Instant.now().toEpochMilli()
        )

        useCase.execute(payload)
    }
}