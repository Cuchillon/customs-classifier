package com.ferick.classifier.configuration

import com.ferick.classifier.common.extensions.upsert
import com.ferick.classifier.configuration.properties.InitialUsersProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(InitialUsersProperties::class)
class WebSecurityConfiguration(
    private val initialUsersProperties: InitialUsersProperties
) {

    @Bean
    fun userDetailsService(
        dataSource: DataSource,
        passwordEncoder: PasswordEncoder
    ): UserDetailsService {
        val users = JdbcUserDetailsManager(dataSource)
        val admin = User.builder()
            .username(initialUsersProperties.admin.username)
            .password(initialUsersProperties.admin.password)
            .passwordEncoder(passwordEncoder::encode)
            .roles(initialUsersProperties.admin.role)
            .build()
        users.upsert(admin)
        initialUsersProperties.services.forEach {
            users.upsert(
                User.builder()
                    .username(it.username)
                    .password(it.password)
                    .passwordEncoder(passwordEncoder::encode)
                    .roles(it.role)
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
