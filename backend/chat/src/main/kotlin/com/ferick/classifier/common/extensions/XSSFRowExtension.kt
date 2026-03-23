package com.ferick.classifier.common.extensions

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFRow
import java.math.BigDecimal

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
