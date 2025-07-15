package br.com.rpdesenvolve.sqs_message_processor_api.adapter.inbound.controller

import br.com.rpdesenvolve.sqs_message_processor_api.application.usecase.PublishMessageUseCase
import br.com.rpdesenvolve.sqs_message_processor_api.common.dto.MessageRequestDTO
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/messages")
class MessageController(
    private val publishMessageUseCase: PublishMessageUseCase
) {
    private val log = LoggerFactory.getLogger(MessageController::class.java)

    @PostMapping
    fun publishMessage(@Valid @RequestBody request: MessageRequestDTO): ResponseEntity<String> {
        log.info("Received publish request: {}", request)
        publishMessageUseCase.execute(request.payload)
        return ResponseEntity.ok("Message published successfully.")
    }
}