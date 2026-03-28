package com.ferick.classifier.configuration

import com.ferick.classifier.configuration.properties.StoreProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
@EnableConfigurationProperties(StoreProperties::class)
class RestClientConfiguration(
    private val storeProperties: StoreProperties
) {

    @Bean
    fun storeRestClient(builder: RestClient.Builder): RestClient = builder
        .baseUrl(storeProperties.url)
        .defaultHeader("X-API-Key", storeProperties.apiKey)
        .build()
}
