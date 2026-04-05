package com.ferick.classifier.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("spring.aws.s3")
data class S3Properties @ConstructorBinding constructor(
    val url: String,
    val region: String,
    val bucket: String,
    val credentials: Credentials
)

data class Credentials @ConstructorBinding constructor(
    val accessKey: String,
    val secretKey: String
)
