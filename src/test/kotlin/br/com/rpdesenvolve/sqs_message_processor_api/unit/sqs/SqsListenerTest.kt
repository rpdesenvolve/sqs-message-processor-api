package br.com.rpdesenvolve.sqs_message_processor_api.unit.sqs

import br.com.rpdesenvolve.sqs_message_processor_api.application.usecase.ProcessMessageUseCase
import br.com.rpdesenvolve.sqs_message_processor_api.common.MessageConverter
import br.com.rpdesenvolve.sqs_message_processor_api.domain.model.MessagePayload
import br.com.rpdesenvolve.sqs_message_processor_api.infrastructure.sqs.SqsListener
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Instant
import kotlin.test.Test

class SqsListenerTest {

    private lateinit var useCase: ProcessMessageUseCase
    private lateinit var converter: MessageConverter
    private lateinit var listener: SqsListener

    @BeforeEach
    fun setup() {
        useCase = mock()
        converter = mock()
        listener = SqsListener(useCase, converter)
    }

    @Test
    fun `must convert and process message correctly`() {
        val payload = MessagePayload("id-123", "message test", Instant.now().toEpochMilli())
        val json = "{\"id\":\"${payload.id}\",\"content\":\"${payload.content}\",\"timestamp\":${payload.timestamp}}"

        whenever(converter.deserialize(eq(json), eq(MessagePayload::class.java))).thenReturn(payload)

        listener.receiveMessage(json)

        verify(converter).deserialize(eq(json), eq(MessagePayload::class.java))
        verify(useCase).execute(eq(payload))
    }
}