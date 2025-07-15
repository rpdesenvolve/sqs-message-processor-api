package br.com.rpdesenvolve.sqs_message_processor_api.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient

@Configuration
@ConfigurationProperties(prefix = "aws")
@Profile("!test")
class AwsSqsConfig {

    @Value("\${aws.accessKeyId}")
    lateinit var accessKeyId: String

    @Value("\${aws.secretAccessKey}")
    lateinit var secretAccessKey: String

    @Value("\${aws.region}")
    lateinit var region: String

    @Bean
    fun sqsClient(): SqsClient {
        val credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey)
        return SqsClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }
}