package com.ferick.classifier.service

interface FileStorageService {
    fun put(key: String, content: ByteArray)
    fun get(key: String): ByteArray
}
