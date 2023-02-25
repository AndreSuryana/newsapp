package com.example.newsapp.data.source.remote

import com.example.newsapp.data.model.ResponseWrapper
import com.example.newsapp.util.constant.SortByConstants.POPULARITY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("language") language: String = "en",
        @Query("pageSize") pageSize: Int = 25,
        @Query("page") page: Int = 1
    ): Response<ResponseWrapper>

    @GET("everything")
    suspend fun getAllArticles(
        @Query("q") keyword: String,
        @Query("sortBy") sortBy: String = POPULARITY,
        @Query("language") language: String = "en",
        @Query("pageSize") pageSize: Int = 25,
        @Query("page") page: Int = 1
    ): Response<ResponseWrapper>
}