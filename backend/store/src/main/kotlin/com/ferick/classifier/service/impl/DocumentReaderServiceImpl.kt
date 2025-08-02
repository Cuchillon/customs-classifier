package com.ferick.classifier.service.impl

import com.ferick.classifier.model.dto.MetaParameter
import com.ferick.classifier.model.dto.StoreMeta
import com.ferick.classifier.service.DocumentReaderService
import com.ferick.classifier.service.readers.FileTypeDocumentReader
import org.springframework.ai.document.Document
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class DocumentReaderServiceImpl(
    private val readers: Set<FileTypeDocumentReader>
) : DocumentReaderService {

    override fun getDocuments(
        resource: Resource,
        meta: StoreMeta,
        fileName: String
    ): List<Document> {
        val reader = readers.find { it.supports(fileName) }
            ?: throw IllegalArgumentException("Type of file $fileName not supported")
        return reader.read(resource, fileName).onEach {
            it.metadata[MetaParameter.CLIENT.key] = meta.client
            it.metadata[MetaParameter.SPECIFICATION.key] = meta.specification
        }
    }
}
