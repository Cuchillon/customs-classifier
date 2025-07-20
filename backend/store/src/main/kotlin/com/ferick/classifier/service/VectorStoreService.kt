package com.ferick.classifier.service

import com.ferick.classifier.model.dto.UserSearchRequest
import org.springframework.ai.document.Document

interface VectorStoreService {
    fun storeDocuments(documents: List<Document>)
    fun searchDocuments(request: UserSearchRequest): List<Document>
}
