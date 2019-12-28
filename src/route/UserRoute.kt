package com.github.leondevlifelog.route

import com.github.leondevlifelog.JWTPrincipal
import com.github.leondevlifelog.JwtConfig
import com.github.leondevlifelog.model.*
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.user() {
    route("/user") {
        authenticate {
            get {
                val principal = call.principal<JWTPrincipal>()
                val user = transaction {
                    User.findById(principal?.id!!)
                }?.let {
                    UserResponse(it.username, it.phone)
                }
                call.respond(BaseResponse.success(user))
            }
        }
        post("login") {
            val loginRequest = call.receive<UserLoginRequest>()
            val user: User? = transaction {
                User.find { (Users.username eq loginRequest.username) }
                    .limit(1).firstOrNull()
            }
            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse.failure("帐号不存在"))
                return@post
            }
            if (user.password == loginRequest.password) {
                call.respond(
                    mapOf("token" to JwtConfig.makeToken(JWTPrincipal(user.id.value, loginRequest.username)))
                )
            } else {
                call.respond(HttpStatusCode.BadRequest, BaseResponse.failure("用户或密码错误"))
            }
        }
        post("register") {
            val registerRequest = call.receive<UserRegisterRequest>()
            val user = transaction {
                User.find { Users.username eq registerRequest.username }.firstOrNull()
            }
            if (user != null) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse.failure("账户已存在"))
                return@post
            }
            val newUser = transaction {
                User.new {
                    username = registerRequest.username
                    password = registerRequest.password
                }
            }
            call.respond(
                BaseResponse.success(
                    mapOf("token" to JwtConfig.makeToken(JWTPrincipal(newUser.id.value, newUser.username)))
                )
            )
        }
    }
}