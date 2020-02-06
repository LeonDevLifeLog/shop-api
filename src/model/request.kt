package com.github.leondevlifelog.model

import org.valiktor.functions.hasSize
import org.valiktor.validate

data class UserLoginRequest(val username: String, val password: String) {
    init {
        validate(this) {
            validate(UserLoginRequest::username).hasSize(4, 16)
            validate(UserLoginRequest::password).hasSize(6, 16)
        }
    }
}

data class UserRegisterRequest(val username: String, val password: String) {
    init {
        validate(this) {
            validate(UserRegisterRequest::username).hasSize(4, 16)
            validate(UserRegisterRequest::password).hasSize(6, 16)
        }
    }
}
