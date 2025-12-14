package com.ferick.classifier.filters

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ferick.classifier.common.extensions.isPermissionEnough
import com.ferick.classifier.configuration.properties.ApiKeyProviderProperties
import com.ferick.classifier.model.ApiKeyAuthentication
import com.ferick.classifier.model.dto.RestApiError
import com.ferick.classifier.model.dto.ValidateRequest
import com.ferick.classifier.service.ApiKeyService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class ApiKeyAuthenticationFilter(
    private val apiKeyProviderProperties: ApiKeyProviderProperties,
    private val apiKeyService: ApiKeyService
) : OncePerRequestFilter() {

    private val objectMapper = jacksonObjectMapper()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getHeader("X-API-Key")?.let { apiKey ->
            val validateResponse = apiKeyService.validateApiKey(ValidateRequest(apiKey))
            if (!validateResponse.valid) {
                sendAuthError(response, HttpServletResponse.SC_UNAUTHORIZED, "API key not valid")
                return
            }

            if (!validateResponse.isPermissionEnough(apiKeyProviderProperties.scopes)) {
                sendAuthError(response, HttpServletResponse.SC_FORBIDDEN, "Not enough permissions")
            }

            val authorities = validateResponse.data?.scopes?.map { SimpleGrantedAuthority(it) } ?: emptyList()
            val authentication = ApiKeyAuthentication(apiKey, authorities)
            authentication.isAuthenticated = true
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } ?: sendAuthError(response, HttpServletResponse.SC_UNAUTHORIZED, "API key not found")
    }

    private fun sendAuthError(response: HttpServletResponse, status: Int, message: String) {
        response.status = status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        if (!response.isCommitted) {
            val error = objectMapper.writeValueAsString(RestApiError(
                statusCode = status,
                errors = mapOf("message" to message)
            ))
            response.writer.write(error)
            response.writer.flush()
        }
    }
}
