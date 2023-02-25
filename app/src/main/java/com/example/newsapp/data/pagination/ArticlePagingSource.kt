package com.example.newsapp.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.ResponseWrapper
import com.example.newsapp.util.Ext.parseErrorResponse
import retrofit2.Response

class ArticlePagingSource(
    val responseCallback: suspend (page: Int) -> Response<ResponseWrapper>
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val response = responseCallback(page)
            val result = response.body()

            if (response.isSuccessful && result!!.status == "ok") {
                LoadResult.Page(
                    data = result.articles,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (result.articles.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable(response.parseErrorResponse().message))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}