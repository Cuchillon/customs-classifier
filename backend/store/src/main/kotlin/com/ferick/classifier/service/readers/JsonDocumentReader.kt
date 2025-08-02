package com.ferick.classifier.service.readers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ferick.classifier.model.dto.FileType
import com.ferick.classifier.model.dto.JsonDocument
import com.ferick.classifier.model.dto.MetaParameter
import org.springframework.ai.document.Document
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class JsonDocumentReader(
    private val objectMapper: ObjectMapper
) : FileTypeDocumentReader {

    override val fileType: FileType = FileType.JSON

    override fun read(resource: Resource, fileName: String): List<Document> {
        val jsonDocument = objectMapper
            .readValue(resource.contentAsByteArray, JsonDocument::class.java)
        return jsonDocument.items.map { item ->
            Document.builder()
                .text(item.text)
                .metadata(MetaParameter.SOURCE.key, fileName)
                .metadata(MetaParameter.CODE.key, item.code)
                .build()
        }
    }
}
