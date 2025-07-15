package br.com.rpdesenvolve.sqs_message_processor_api.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MessageConverter(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) {
    private val log = LoggerFactory.getLogger(MessageConverter::class.java)

    fun <T> serialize(obj: T): String {
        return try {
            objectMapper.writeValueAsString(obj)
        } catch (ex: Exception) {
            log.error("Failed to serialize object: $obj", ex)
            throw RuntimeException("Error serializing message", ex)
        }
    }

    fun <T> deserialize(json: String, clazz: Class<T>): T {
        return try {
            objectMapper.readValue(json, clazz)
        } catch (ex: Exception) {
            log.error("Failed to deserialize JSON: $json", ex)
            throw RuntimeException("Error deserializing message", ex)
        }
    }
}