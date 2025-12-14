package com.ferick.classifier.exceptions

import org.springframework.security.core.AuthenticationException

class ApiKeyProviderException(message: String) : AuthenticationException(message)
