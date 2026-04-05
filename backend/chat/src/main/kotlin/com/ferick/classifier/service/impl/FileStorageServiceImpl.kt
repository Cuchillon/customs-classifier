package com.ferick.classifier.service.impl

import com.ferick.classifier.configuration.properties.S3Properties
import com.ferick.classifier.service.FileStorageService
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Response

@Service
class FileStorageServiceImpl(
    private val s3Properties: S3Properties,
    private val s3Client: S3Client
) : FileStorageService {

    override fun put(key: String, content: ByteArray) {
        val request = PutObjectRequest.builder().bucket(s3Properties.bucket).key(key).build()
        checkResult(s3Client.putObject(request, RequestBody.fromBytes(content)))
    }

    override fun get(key: String): ByteArray {
        val request = GetObjectRequest.builder().bucket(s3Properties.bucket).key(key).build()
        val responseBytes = s3Client.getObjectAsBytes(request)
        checkResult(responseBytes.response())
        return responseBytes.asByteArray()
    }

    private fun checkResult(response: S3Response) {
        if (response.sdkHttpResponse() == null || !response.sdkHttpResponse().isSuccessful) {
            val requestId = response.responseMetadata().requestId()
            val errorMessage = """Failed to operate with file in s3
                |Request ID: $requestId
                |Status text: ${response.sdkHttpResponse().statusText().orElse("empty")}
            """.trimMargin()
            throw IllegalStateException(errorMessage)
        }
    }
}
