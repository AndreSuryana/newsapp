package com.example.newsapp.util

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()

    data class Failure<out T>(
        val message: String? = "An error occurred"
    ) : Resource<T>()

    data class Error<out T>(
        val message: String? = "An error occurred"
    ) : Resource<T>()
}
