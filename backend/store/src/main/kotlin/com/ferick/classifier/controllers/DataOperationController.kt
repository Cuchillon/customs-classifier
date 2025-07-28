package com.ferick.classifier.controllers

import com.ferick.classifier.model.dto.StoreMeta
import com.ferick.classifier.model.dto.StoreRequest
import com.ferick.classifier.model.dto.UserSearchRequest
import com.ferick.classifier.service.DataOperationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController("/api/v1")
class DataOperationController(
    private val dataOperationService: DataOperationService
) {

    @PostMapping("/store")
    fun storeData(
        @RequestPart meta: StoreMeta,
        @RequestPart data: MultipartFile
    ) = dataOperationService.storeData(
        StoreRequest(meta, data.bytes)
    )

    @PostMapping("/search")
    fun searchData(@RequestBody request: UserSearchRequest) =
        dataOperationService.searchData(request)
}
