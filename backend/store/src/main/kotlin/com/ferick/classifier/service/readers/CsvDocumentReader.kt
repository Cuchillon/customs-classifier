package com.ferick.classifier.service.readers

import com.ferick.classifier.model.documents.CsvDocument
import com.ferick.classifier.model.enums.FileType
import com.ferick.classifier.model.documents.Specification
import com.ferick.classifier.model.enums.MetaParameter
import com.opencsv.bean.CsvToBeanBuilder
import org.springframework.ai.document.Document
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.StringReader
import java.nio.charset.Charset

@Component
class CsvDocumentReader : FileTypeDocumentReader {

    override val fileType: FileType = FileType.CSV

    override fun read(resource: Resource, fileName: String): List<Document> {
        val csvToBean = CsvToBeanBuilder<CsvDocument>(
            StringReader(resource.getContentAsString(Charset.defaultCharset()))
        )
            .withType(CsvDocument::class.java)
            .withSeparator(';')
            .withSkipLines(1)
            .build()
        val specification = csvToBean.parse()
            .map { it.toSpecificationItem() }
            .let { Specification(it) }
        return specification.items.map { item ->
            Document.builder()
                .text(item.text)
                .metadata(MetaParameter.SOURCE.key, fileName)
                .metadata(MetaParameter.CODE.key, item.code)
                .build()
        }
    }
}
