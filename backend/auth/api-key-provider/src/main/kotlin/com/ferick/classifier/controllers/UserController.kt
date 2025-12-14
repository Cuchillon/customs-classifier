package com.ferick.classifier.controllers

import com.ferick.classifier.common.extensions.upsert
import com.ferick.classifier.model.dto.UserUpsertRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userDetailsManager: UserDetailsManager,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping
    fun upsertUser(@RequestBody request: UserUpsertRequest) {
        val user = User.builder()
            .username(request.username)
            .password(request.password)
            .passwordEncoder(passwordEncoder::encode)
            .roles(*request.roles.map { it.name }.toTypedArray())
            .build()
        userDetailsManager.upsert(user)
    }

    @DeleteMapping("/{username}")
    fun deleteUser(@PathVariable username: String): ResponseEntity<Unit> {
        if (userDetailsManager.userExists(username)) {
            userDetailsManager.deleteUser(username)
            return ResponseEntity.ok().build()
        } else {
            return ResponseEntity.notFound().build()
        }
    }
}
