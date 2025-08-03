package com.ferick.classifier.model.enums

enum class SpecificationHeader(val variants: Set<String>) {
    CODE(setOf("Код", "Код товара", "Код ТНВЭД")),
    DESCRIPTION(setOf("Наименование", "Наименование товара", "Описание", "Описание товара"));

    private fun validate(header: String): Boolean = header in this.variants

    companion object {
        fun validateAndGet(header: String): SpecificationHeader {
            val result = SpecificationHeader.entries.find { it.validate(header) }
                ?: throw IllegalArgumentException(
                    "Headers must be only ${CODE.variants} or ${DESCRIPTION.variants}"
                )
            return result
        }
    }
}
