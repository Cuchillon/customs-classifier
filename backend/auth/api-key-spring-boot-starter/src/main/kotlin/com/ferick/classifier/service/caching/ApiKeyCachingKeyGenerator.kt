package com.ferick.classifier.service.caching

import com.ferick.classifier.exceptions.ApiKeyCachingException
import com.ferick.classifier.model.dto.ValidateRequest
import org.springframework.cache.interceptor.KeyGenerator
import java.lang.reflect.Method

class ApiKeyCachingKeyGenerator : KeyGenerator {

    override fun generate(target: Any, method: Method, vararg params: Any): String {
        val request = params.firstOrNull() as? ValidateRequest
            ?: throw ApiKeyCachingException("First param must be ValidateRequest")

        val parts = request.apiKey.split("_", limit = 3)
        if (parts.size != 3 || parts[0] != "sk") {
            throw ApiKeyCachingException("API key is invalid")
        }

        return parts[1]
    }
}
