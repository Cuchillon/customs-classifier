package com.ferick.classifier.service.impl

import com.ferick.classifier.model.dto.MetaParameter
import com.ferick.classifier.model.dto.StoreMeta
import com.ferick.classifier.service.DocumentReaderService
import org.springframework.ai.document.Document
import org.springframework.ai.reader.tika.TikaDocumentReader
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class DocumentReaderServiceImpl : DocumentReaderService {

    override fun getDocuments(resource: Resource, meta: StoreMeta): List<Document> =
        TikaDocumentReader(resource).read().onEach {
            it.metadata[MetaParameter.CLIENT.key] = meta.client
            it.metadata[MetaParameter.SPECIFICATION.key] = meta.specification
        }
}
