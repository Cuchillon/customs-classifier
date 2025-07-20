package com.ferick.classifier.service

import com.ferick.classifier.model.dto.StoreRequestMeta
import org.springframework.ai.document.Document
import org.springframework.core.io.Resource

interface DocumentReaderService {
    fun getDocuments(resource: Resource, meta: StoreRequestMeta): List<Document>
}
