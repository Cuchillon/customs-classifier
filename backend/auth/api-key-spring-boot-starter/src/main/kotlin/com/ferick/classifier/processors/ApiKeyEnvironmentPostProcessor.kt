package com.ferick.classifier.processors

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.io.ClassPathResource
import java.io.IOException

class ApiKeyEnvironmentPostProcessor : EnvironmentPostProcessor {

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        val defaultResource = ClassPathResource("api-key-default.yml")
        if (defaultResource.exists()) {
            try {
                val yamlLoader = YamlPropertySourceLoader()
                val propertySource = yamlLoader.load("api-key-default", defaultResource)[0]
                environment.propertySources.addLast(propertySource)
            } catch (e: IOException) {
                throw IllegalStateException("Failed to load api-key-default.yml", e)
            }
        }
    }
}
