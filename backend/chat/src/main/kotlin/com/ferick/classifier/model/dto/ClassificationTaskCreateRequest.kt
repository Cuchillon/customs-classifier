package com.ferick.classifier.model.dto

data class ClassificationTaskCreateRequest(
    val meta: ClassificationTaskMeta,
    val data: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClassificationTaskCreateRequest

        if (meta != other.meta) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = meta.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}
