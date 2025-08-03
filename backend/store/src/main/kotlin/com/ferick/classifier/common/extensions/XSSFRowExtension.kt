package com.ferick.classifier.common.extensions

import com.ferick.classifier.model.enums.SpecificationHeader
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFRow
import java.math.BigDecimal
import java.util.EnumMap

fun XSSFRow.getHeaders(): EnumMap<SpecificationHeader, Int> {
    val headers = EnumMap<SpecificationHeader, Int>(SpecificationHeader::class.java)
    (0 until this.lastCellNum).map { index ->
        val cell = this.getCell(index)
        if (cell != null) {
            val value = cell.stringCellValue
            val header = SpecificationHeader.validateAndGet(value)
            headers.put(header, index)
        } else {
            throw IllegalArgumentException("Header must not be empty, cell index $index")
        }
    }
    if (headers.size != SpecificationHeader.entries.size) {
        throw IllegalArgumentException(
            """Headers must be in
                | ${SpecificationHeader.entries.flatMap { it.variants }}
            """.trimMargin()
        )
    }
    return headers
}

fun XSSFRow.getCellValue(index: Int): String {
    val cell = this.getCell(index)
    return when (cell.cellType) {
        CellType.NUMERIC -> {
            BigDecimal(cell.toString()).stripTrailingZeros().toPlainString()
        }
        CellType.FORMULA -> {
            cell.rawValue ?: ""
        }
        else -> {
            cell.stringCellValue ?: ""
        }
    }
}
