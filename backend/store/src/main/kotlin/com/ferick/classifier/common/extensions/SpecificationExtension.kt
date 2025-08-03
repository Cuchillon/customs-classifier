package com.ferick.classifier.common.extensions

import com.ferick.classifier.model.documents.Specification
import com.ferick.classifier.model.enums.MetaParameter
import org.springframework.ai.document.Document

fun Specification.toDocuments(fileName: String): List<Document> =
    this.items.map { item ->
        Document.builder()
            .text(item.text)
            .metadata(MetaParameter.SOURCE.key, fileName)
            .metadata(MetaParameter.CODE.key, item.code)
            .build()
    }
