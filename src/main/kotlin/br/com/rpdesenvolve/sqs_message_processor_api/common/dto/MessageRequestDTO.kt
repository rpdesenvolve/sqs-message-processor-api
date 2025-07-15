package br.com.rpdesenvolve.sqs_message_processor_api.common.dto

import jakarta.validation.constraints.NotBlank

data class MessageRequestDTO(
    @field:NotBlank(message = "Payload cannot be blank")
    val payload: String
)
