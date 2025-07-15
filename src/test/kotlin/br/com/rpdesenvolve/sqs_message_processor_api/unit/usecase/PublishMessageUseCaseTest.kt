package br.com.rpdesenvolve.sqs_message_processor_api.unit.usecase

import br.com.rpdesenvolve.sqs_message_processor_api.application.usecase.PublishMessageUseCase
import br.com.rpdesenvolve.sqs_message_processor_api.common.MessageConverter
import br.com.rpdesenvolve.sqs_message_processor_api.infrastructure.sqs.SqsPublisherGateway
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.UUID
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class PublishMessageUseCaseTest {
    @Mock
    lateinit var gateway: SqsPublisherGateway

    @Mock
    lateinit var converter: MessageConverter

    @InjectMocks
    lateinit var useCase: PublishMessageUseCase

    @Test
    fun `must call gateway when publishing`() {
        val content = "message"
        val fakeId = UUID.randomUUID().toString()
        val fakeJson = "{\"id\":\"$fakeId\",\"content\":\"message\",\"timestamp\":1234567890}"

        whenever(converter.serialize(any<Any>())).thenReturn(fakeJson)

        useCase.execute(content)

        verify(gateway).sendMessage(fakeJson)
    }
}