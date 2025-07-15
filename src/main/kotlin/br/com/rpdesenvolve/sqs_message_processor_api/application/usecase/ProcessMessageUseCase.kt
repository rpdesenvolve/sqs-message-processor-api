package br.com.rpdesenvolve.sqs_message_processor_api.application.usecase

import br.com.rpdesenvolve.sqs_message_processor_api.domain.model.MessagePayload
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProcessMessageUseCase {
    private val log = LoggerFactory.getLogger(ProcessMessageUseCase::class.java)

    fun execute(payload: MessagePayload) {
        log.info("Processing message payload: {}", payload)
    }
}