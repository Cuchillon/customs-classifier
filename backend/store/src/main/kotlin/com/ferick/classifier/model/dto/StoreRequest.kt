package com.ferick.classifier.model.dto

data class StoreRequest(
    val meta: StoreMeta,
    val data: ByteArray,
    val fileName: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StoreRequest

        if (meta != other.meta) return false
        if (!data.contentEquals(other.data)) return false
        if (fileName != other.fileName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = meta.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + (fileName?.hashCode() ?: 0)
        return result
    }
}
