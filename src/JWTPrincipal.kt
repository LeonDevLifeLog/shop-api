package com.github.leondevlifelog

import io.ktor.auth.Principal

data class JWTPrincipal(val id: Int, val username: String) : Principal