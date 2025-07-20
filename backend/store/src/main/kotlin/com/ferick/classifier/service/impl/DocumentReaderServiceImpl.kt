package com.ferick.classifier.service.impl

import com.ferick.classifier.model.dto.StoreRequestMeta
import com.ferick.classifier.service.DocumentReaderService
import org.springframework.ai.document.Document
import org.springframework.ai.reader.tika.TikaDocumentReader
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class DocumentReaderServiceImpl : DocumentReaderService {

    override fun getDocuments(resource: Resource, meta: StoreRequestMeta): List<Document> =
        TikaDocumentReader(resource).read().onEach {
            it.metadata["client"] = meta.client
            it.metadata["specification"] = meta.specification
        }
}
