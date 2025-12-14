package com.ferick.classifier.configuration

import com.ferick.classifier.common.extensions.upsert
import com.ferick.classifier.configuration.properties.InitialUsersProperties
import com.ferick.classifier.model.dto.Role
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(InitialUsersProperties::class)
class WebSecurityConfiguration(
    private val initialUsersProperties: InitialUsersProperties
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                authorize("/api/v1/user/**", hasRole(Role.ADMIN.name))
                authorize("/api/v1/api-key/generate", hasAnyRole(Role.USER.name, Role.ADMIN.name))
                authorize("/api/v1/api-key/validate", hasRole(Role.SERVICE.name))
                authorize(anyRequest, authenticated)
            }
            httpBasic {}
            formLogin { disable() }
        }
        return http.build()
    }

    @Bean
    fun userDetailsManager(
        dataSource: DataSource,
        passwordEncoder: PasswordEncoder
    ): UserDetailsManager {
        val users = JdbcUserDetailsManager(dataSource)
        val admin = User.builder()
            .username(initialUsersProperties.admin.username)
            .password(initialUsersProperties.admin.password)
            .passwordEncoder(passwordEncoder::encode)
            .roles(initialUsersProperties.admin.role.name)
            .build()
        users.upsert(admin)
        initialUsersProperties.services.forEach {
            users.upsert(
                User.builder()
                    .username(it.username)
                    .password(it.password)
                    .passwordEncoder(passwordEncoder::encode)
                    .roles(it.role.name)
                    .build()
            )
        }
        return users
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
