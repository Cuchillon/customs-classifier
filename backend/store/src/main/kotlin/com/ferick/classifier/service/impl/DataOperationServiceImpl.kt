package com.ferick.classifier.service.impl

import com.ferick.classifier.common.extensions.toSearchResponseItem
import com.ferick.classifier.model.dto.StoreRequest
import com.ferick.classifier.model.dto.UserSearchRequest
import com.ferick.classifier.model.dto.UserSearchResponse
import com.ferick.classifier.service.DataOperationService
import com.ferick.classifier.service.DocumentReaderService
import com.ferick.classifier.service.VectorStoreService
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service

@Service
class DataOperationServiceImpl(
    private val documentReaderService: DocumentReaderService,
    private val vectorStoreService: VectorStoreService
) : DataOperationService {

    override fun storeData(request: StoreRequest) {
        val resource = ByteArrayResource(request.data)
        val documents = documentReaderService.getDocuments(resource, request.meta)
        vectorStoreService.storeDocuments(documents)
    }

    override fun searchData(request: UserSearchRequest): UserSearchResponse =
        vectorStoreService.searchDocuments(request)
            .map { it.toSearchResponseItem() }
            .let { UserSearchResponse(it) }
}
