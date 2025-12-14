package com.ferick.classifier.configuration

import com.ferick.classifier.configuration.properties.ApiKeyProviderProperties
import com.ferick.classifier.filters.ApiKeyAuthenticationFilter
import com.ferick.classifier.service.ApiKeyService
import com.ferick.classifier.service.impl.ApiKeyServiceImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableConfigurationProperties(ApiKeyProviderProperties::class)
@ConditionalOnProperty(prefix = "api-key-provider", name = ["enabled"], havingValue = "true", matchIfMissing = false)
@EnableWebSecurity
class ApiKeyFilterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = ["apiKeyService"])
    fun apiKeyService(): ApiKeyService = ApiKeyServiceImpl()

    @Bean
    @ConditionalOnMissingBean(name = ["apiKeyAuthenticationFilter"])
    fun apiKeyAuthenticationFilter(apiKeyService: ApiKeyService): ApiKeyAuthenticationFilter =
        ApiKeyAuthenticationFilter(apiKeyService)

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
