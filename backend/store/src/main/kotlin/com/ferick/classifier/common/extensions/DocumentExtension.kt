package com.ferick.classifier.common.extensions

import com.ferick.classifier.model.enums.MetaParameter
import com.ferick.classifier.model.dto.StoreMeta
import com.ferick.classifier.model.dto.UserSearchResponseItem
import org.springframework.ai.document.Document

fun Document.toSearchResponseItem(): UserSearchResponseItem = UserSearchResponseItem(
    code = this.metadata[MetaParameter.CODE.key].toString(),
    text = this.text.toString(),
    score = this.score ?: 0.0,
    meta = StoreMeta(
        client = this.metadata[MetaParameter.CLIENT.key].toString(),
        specification = this.metadata[MetaParameter.SPECIFICATION.key].toString()
    )
)
