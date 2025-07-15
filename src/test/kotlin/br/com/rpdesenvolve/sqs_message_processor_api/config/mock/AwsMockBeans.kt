package br.com.rpdesenvolve.sqs_message_processor_api.config.mock

import org.mockito.Mockito.mock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.services.sqs.SqsClient

@Configuration
@Profile("test")
class AwsMockBeans {

    @Bean
    fun sqsClient(): SqsClient {
        return mock(SqsClient::class.java)
    }
}