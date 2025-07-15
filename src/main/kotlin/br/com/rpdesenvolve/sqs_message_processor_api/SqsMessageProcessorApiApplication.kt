package br.com.rpdesenvolve.sqs_message_processor_api

import io.awspring.cloud.messaging.config.annotation.EnableSqs
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SqsMessageProcessorApiApplication

fun main(args: Array<String>) {
	runApplication<SqsMessageProcessorApiApplication>(*args)
}
