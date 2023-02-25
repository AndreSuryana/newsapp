package com.example.newsapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.ResponseWrapper
import com.example.newsapp.data.pagination.ArticlePagingSource
import com.example.newsapp.data.source.local.NewsAppDatabase
import com.example.newsapp.data.source.remote.NewsApiService
import com.example.newsapp.util.Ext.parseErrorResponse
import com.example.newsapp.util.NewsCategory
import com.example.newsapp.util.Resource
import com.example.newsapp.util.constant.SortByConstants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 25

class NewsRepositoryImpl @Inject constructor(
    private val local: NewsAppDatabase,
    private val remote: NewsApiService
) : NewsRepository {

    override suspend fun getTopHeadlines(): Resource<ResponseWrapper> {
        return try {
            val response = remote.getTopHeadlines(pageSize = 25)
            val result = response.body()

            if (response.isSuccessful && result!!.status == "ok") {
                Resource.Success(result)
            } else {
                Resource.Failure(response.parseErrorResponse().message)
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun getExploreArticles(category: NewsCategory): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                ArticlePagingSource { page ->
                    remote.getAllArticles(
                        keyword = category.keyword,
                        sortBy = SortByConstants.RELEVANCY,
                        page = page
                    )
                }
            }
        ).flow
    }

    override suspend fun searchArticles(keyword: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                ArticlePagingSource { page ->
                    remote.getAllArticles(
                        keyword = keyword,
                        sortBy = SortByConstants.RELEVANCY,
                        page = page
                    )
                }
            }
        ).flow
    }

    override suspend fun bookmarkArticle(article: Article): Resource<Boolean> {
        return try {
            local.bookmarkDao().insertArticle(article.toArticleEntity())
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun removeBookmarkedArticle(article: Article): Resource<Boolean> {
        return try {
            local.bookmarkDao().removeArticle(article.toArticleEntity())
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun getBookmarkedArticles(): Resource<List<Article>> {
        return try {
            val result = local.bookmarkDao().getAllArticles()
            Resource.Success(result.map {
                it.toArticle()
            })
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun isArticleBookmarked(article: Article): Resource<Boolean> {
        return try {
            val result = local.bookmarkDao().isArticleBookmarked(article.articleUrl)
            Log.d("Repository", "$result")
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}