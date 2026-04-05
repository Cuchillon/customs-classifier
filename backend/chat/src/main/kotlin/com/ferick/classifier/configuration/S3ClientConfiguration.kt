package com.ferick.classifier.configuration

import com.ferick.classifier.configuration.properties.S3Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Configuration
class S3ClientConfiguration {

    @Bean
    fun s3Client(s3Properties: S3Properties): S3Client {
        val credentials = AwsBasicCredentials.create(
            s3Properties.credentials.accessKey,
            s3Properties.credentials.secretKey
        )
        val region = Region.of(s3Properties.region)
        return S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(region)
            .endpointOverride(URI.create(s3Properties.url))
            .build()
    }
}
