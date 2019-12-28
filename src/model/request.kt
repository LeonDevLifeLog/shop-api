package com.github.leondevlifelog.model

data class UserLoginRequest(val username: String, val password: String)
data class UserRegisterRequest(val username: String, val password: String)