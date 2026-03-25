package com.ferick.classifier.service.documents

import com.ferick.classifier.model.dto.ClassificationResult
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream

@Component
class ExcelDocumentPrinter {

    fun print(result: ClassificationResult): ByteArray {
        val workbook = SXSSFWorkbook()
        val sheet = workbook.createSheet("Results").apply {
            setRandomAccessWindowSize(SXSSFWorkbook.DEFAULT_WINDOW_SIZE)
        }

        val headers = sheet.createRow(0)
        headers.createCell(0).setCellValue("Код товара")
        headers.createCell(1).setCellValue("Наименование товара")

        result.items.forEach { item ->
            val newRow = sheet.createRow(sheet.physicalNumberOfRows)
            newRow.createCell(0).setCellValue(item.code)
            newRow.createCell(1).setCellValue(item.text)
        }

        val bos = ByteArrayOutputStream()
        workbook.write(bos)
        return bos.toByteArray()
    }
}
