package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseWrapper(

    @SerializedName("status")
    val status: String,

    @SerializedName("code")
    val code: String?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("totalResults")
    val total: Int?,

    @SerializedName("articles")
    val articles: List<Article>
)