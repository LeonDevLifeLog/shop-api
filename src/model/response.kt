package com.github.leondevlifelog.model

data class BaseResponse<T> private constructor(val message: String?, val data: T?) {
    companion object {
        fun <T> success(data: T?): BaseResponse<T> {
            return BaseResponse(null, data)
        }

        fun failure(message: String): BaseResponse<Any> {
            return BaseResponse(message, null)
        }
    }
}

data class UserResponse(val username: String, val phone: String?)