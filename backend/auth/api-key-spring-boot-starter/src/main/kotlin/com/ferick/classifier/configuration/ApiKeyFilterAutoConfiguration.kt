package com.ferick.classifier.configuration

import com.ferick.classifier.configuration.properties.ApiKeyProviderProperties
import com.ferick.classifier.filters.ApiKeyAuthenticationFilter
import com.ferick.classifier.service.ApiKeyService
import com.ferick.classifier.service.caching.ApiKeyCachingKeyGenerator
import com.ferick.classifier.service.impl.ApiKeyServiceImpl
import com.google.common.cache.CacheBuilder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.client.RestClient
import java.util.concurrent.TimeUnit

@Configuration
@EnableConfigurationProperties(ApiKeyProviderProperties::class)
@ConditionalOnProperty(prefix = "api-key-provider", name = ["enabled"], havingValue = "true", matchIfMissing = false)
@EnableWebSecurity
@EnableCaching
class ApiKeyFilterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = ["apiKeyProviderClient"])
    fun apiKeyProviderClient(apiKeyProviderProperties: ApiKeyProviderProperties): RestClient =
        RestClient.builder()
            .baseUrl(apiKeyProviderProperties.url)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeaders {
                it.setBasicAuth(apiKeyProviderProperties.username, apiKeyProviderProperties.password)
            }
            .build()

    @Bean
    @ConditionalOnMissingBean(name = ["apiKeyCacheManager"])
    fun apiKeyCacheManager(): CacheManager = object : ConcurrentMapCacheManager() {
        override fun createConcurrentMapCache(name: String): Cache =
            ConcurrentMapCache(
                name,
                CacheBuilder.newBuilder()
                    .expireAfterAccess(1, TimeUnit.HOURS)
                    .build<Any, Any>().asMap(),
                false
            )
    }

    @Bean
    @ConditionalOnMissingBean(name = ["apiKeyCachingKeyGenerator"])
    fun apiKeyCachingKeyGenerator(): ApiKeyCachingKeyGenerator = ApiKeyCachingKeyGenerator()

    @Bean
    @ConditionalOnMissingBean(name = ["apiKeyService"])
    fun apiKeyService(apiKeyProviderClient: RestClient): ApiKeyService =
        ApiKeyServiceImpl(apiKeyProviderClient)

    @Bean
    @ConditionalOnMissingBean(name = ["apiKeyAuthenticationFilter"])
    fun apiKeyAuthenticationFilter(
        apiKeyProviderProperties: ApiKeyProviderProperties,
        apiKeyService: ApiKeyService
    ): ApiKeyAuthenticationFilter =
        ApiKeyAuthenticationFilter(apiKeyProviderProperties, apiKeyService)

    @Bean
    @ConditionalOnMissingBean(name = ["apiKeySecurityFilterChain"])
    fun apiKeySecurityFilterChain(
        http: HttpSecurity,
        apiKeyAuthenticationFilter: ApiKeyAuthenticationFilter
    ): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(apiKeyAuthenticationFilter)
            httpBasic { disable() }
            formLogin { disable() }
        }
        return http.build()
    }
}
