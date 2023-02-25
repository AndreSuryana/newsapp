package com.example.newsapp.data.repository

import androidx.paging.PagingData
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.ResponseWrapper
import com.example.newsapp.util.NewsCategory
import com.example.newsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getTopHeadlines(): Resource<ResponseWrapper>

    suspend fun getExploreArticles(category: NewsCategory = NewsCategory.values().first()): Flow<PagingData<Article>>

    suspend fun searchArticles(keyword: String): Flow<PagingData<Article>>

    suspend fun bookmarkArticle(article: Article): Resource<Boolean>

    suspend fun removeBookmarkedArticle(article: Article): Resource<Boolean>

    suspend fun getBookmarkedArticles(): Resource<List<Article>>

    suspend fun isArticleBookmarked(article: Article): Resource<Boolean>
}