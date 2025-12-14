package com.ferick.classifier.exceptions

import org.springframework.security.core.AuthenticationException

class ApiKeyCachingException(message: String) : AuthenticationException(message)
