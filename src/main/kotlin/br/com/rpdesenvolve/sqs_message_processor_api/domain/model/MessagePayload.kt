package br.com.rpdesenvolve.sqs_message_processor_api.domain.model

data class MessagePayload(
    val id: String,
    val content: String,
    val timestamp: Long
)
