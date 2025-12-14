package com.ferick.classifier.common.extensions

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.UserDetailsManager

fun UserDetailsManager.upsert(user: UserDetails) =
    if (this.userExists(user.username)) {
        this.updateUser(user)
    } else {
        this.createUser(user)
    }
