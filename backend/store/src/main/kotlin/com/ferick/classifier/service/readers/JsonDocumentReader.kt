package com.ferick.classifier.service.readers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ferick.classifier.common.extensions.toDocuments
import com.ferick.classifier.model.documents.Specification
import com.ferick.classifier.model.enums.FileType
import org.springframework.ai.document.Document
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class JsonDocumentReader(
    private val objectMapper: ObjectMapper
) : FileTypeDocumentReader {

    override val fileType: FileType = FileType.JSON

    override fun read(resource: Resource, fileName: String): List<Document> {
        val specification = objectMapper
            .readValue(resource.contentAsByteArray, Specification::class.java)
        return specification.toDocuments(fileName)
    }
}
