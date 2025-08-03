package com.ferick.classifier.service.readers

import com.ferick.classifier.common.extensions.getHeaders
import com.ferick.classifier.common.extensions.toDocuments
import com.ferick.classifier.model.documents.Specification
import com.ferick.classifier.model.documents.SpecificationItem
import com.ferick.classifier.model.enums.FileType
import com.ferick.classifier.model.enums.SpecificationHeader
import org.apache.poi.openxml4j.util.ZipSecureFile
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.ai.document.Document
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.File
import java.util.EnumMap

@Component
class ExcelDocumentReader : FileTypeDocumentReader {

    override val fileType: FileType = FileType.XLSX

    override fun read(resource: Resource, fileName: String): List<Document> {
        val specification = parseExcelFile(resource.file)
        return specification.toDocuments(fileName)
    }

    private fun parseExcelFile(file: File): Specification {
        val workbook = createWorkbook(file)
        val sheet = workbook.getSheetAt(0)
        val headers = getHeaders(sheet)
        return getSpecification(sheet, headers)
    }

    private fun createWorkbook(file: File): XSSFWorkbook {
        ZipSecureFile.setMinInflateRatio(MIN_INFLATE_RATIO)
        return XSSFWorkbook(file)
    }

    private fun getHeaders(sheet: XSSFSheet): EnumMap<SpecificationHeader, Int> {
        val rowWithHeaders = sheet.getRow(0)
        return rowWithHeaders.getHeaders()
    }

    private fun getSpecification(
        sheet: XSSFSheet,
        headers: EnumMap<SpecificationHeader, Int>
    ): Specification {
        val items = mutableListOf<SpecificationItem>()
        for ( index in 1 until sheet.count()) {
            val row = sheet.getRow(index) ?: break
            val codeIndex = headers[SpecificationHeader.CODE]!!
            val textIndex = headers[SpecificationHeader.DESCRIPTION]!!
            items.add(
                SpecificationItem(
                    code = row.getCell(codeIndex)?.stringCellValue ?: "",
                    text = row.getCell(textIndex)?.stringCellValue ?: ""
                )
            )
        }
        return Specification(items)
    }

    companion object {
        private const val MIN_INFLATE_RATIO = 0.001
    }
}
