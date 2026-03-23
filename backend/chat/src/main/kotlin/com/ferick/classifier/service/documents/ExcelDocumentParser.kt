package com.ferick.classifier.service.documents

import com.ferick.classifier.common.extensions.getCellValue
import org.apache.poi.openxml4j.util.ZipSecureFile
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class ExcelDocumentParser {

    fun parse(resource: Resource): List<String> {
        val workbook = createWorkbook(resource.inputStream)
        val sheet = workbook.getSheetAt(0)
        return getRows(sheet)
    }

    private fun createWorkbook(inputStream: InputStream): XSSFWorkbook {
        ZipSecureFile.setMinInflateRatio(MIN_INFLATE_RATIO)
        return XSSFWorkbook(inputStream)
    }

    private fun getRows(sheet: XSSFSheet): List<String> {
        val items = mutableListOf<String>()
        for ( index in 1 until sheet.count()) {
            val row = sheet.getRow(index) ?: break
            items.add(row.getCellValue(0))
        }
        return items
    }

    companion object {
        private const val MIN_INFLATE_RATIO = 0.001
    }
}
