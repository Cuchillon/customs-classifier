package com.ferick.classifier.service.readers

import com.ferick.classifier.model.enums.FileType
import org.springframework.ai.document.Document
import org.springframework.core.io.Resource

interface FileTypeDocumentReader {
    val fileType: FileType

    fun supports(type: FileType) = type == fileType

    fun supports(fileName: String): Boolean = fileName.endsWith(fileType.extension)

    fun read(resource: Resource, fileName: String): List<Document>
}
