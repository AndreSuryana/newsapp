package com.example.newsapp.data.source.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketTimeoutException

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            val response = chain.proceed(request)
            response.newBuilder()
                .body(response.body)
                .build()
        } catch (e: Exception) {
            val code: Int
            val message: String
            when (e) {
                is SocketTimeoutException -> {
                    code = 599
                    message = "Network Connect Timeout Error"
                }
                else -> {
                    code = -1
                    message = "Unknown Error"
                }
            }
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(code)
                .message(message)
                .body("{${e}}".toResponseBody(null)).build()
        }
    }
}