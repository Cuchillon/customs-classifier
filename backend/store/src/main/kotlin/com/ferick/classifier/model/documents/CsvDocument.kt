package com.ferick.classifier.model.documents

import com.opencsv.bean.CsvBindByPosition

class CsvDocument {
    @CsvBindByPosition(position = 0)
    private lateinit var code: String

    @CsvBindByPosition(position = 1)
    private lateinit var text: String

    fun toSpecificationItem() = SpecificationItem(code, text)
}
