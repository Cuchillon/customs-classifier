package com.ferick.classifier.service

import com.ferick.classifier.model.dto.StoreMeta
import org.springframework.ai.document.Document
import org.springframework.core.io.Resource

interface DocumentReaderService {
    fun getDocuments(
        resource: Resource,
        meta: StoreMeta,
        fileName: String
    ): List<Document>
}
