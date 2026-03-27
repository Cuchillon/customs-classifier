package com.ferick.classifier.service

import com.ferick.classifier.model.dto.StoreRequest
import com.ferick.classifier.model.dto.UserSearchBatchRequest
import com.ferick.classifier.model.dto.UserSearchRequest
import com.ferick.classifier.model.dto.UserSearchResponse

interface DataOperationService {
    fun storeData(request: StoreRequest)
    fun searchData(request: UserSearchRequest): UserSearchResponse
    fun batchSearchData(request: UserSearchBatchRequest): UserSearchResponse
}
