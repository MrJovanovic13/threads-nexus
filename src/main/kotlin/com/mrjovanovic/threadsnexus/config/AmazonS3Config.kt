package com.mrjovanovic.threadsnexus.config

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.*
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.net.url.Url
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonS3Config {

    @Value("\${amazon.properties.region}")
    private lateinit var awsRegion: String

    @Value("\${amazon.properties.endpoint.url}")
    private lateinit var awsEndpointUrl: String

    @Value("\${amazon.properties.access.key}")
    private lateinit var awsAccessKey: String

    @Value("\${amazon.properties.secret.key}")
    private lateinit var awsSecretKey: String

    @Bean
    fun amazonS3(): S3Client {
        return runBlocking {
            S3Client.fromEnvironment {
                region = awsRegion
                endpointUrl = Url.parse(awsEndpointUrl)
                credentialsProvider = StaticCredentialsProvider(Credentials(awsAccessKey, awsSecretKey))
            }
        }
    }
}
