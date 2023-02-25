package com.example.newsapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    data class DetailUiState(
        var error: ((Boolean, String?) -> Unit)? = null
    )

    var detailUiState by mutableStateOf(DetailUiState())

    private val _onBookmarkChanged = MutableSharedFlow<Boolean>()
    val onBookmarkChanged = _onBookmarkChanged.asSharedFlow()

    fun isArticleBookmarked(article: Article?): Boolean = runBlocking(Dispatchers.IO) {
        if (article != null) {
            when (val result = repository.isArticleBookmarked(article)) {
                is Resource.Success -> result.data
                else -> false
            }
        } else false
    }

    fun updateBookmarkArticle(article: Article, bookmarked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (bookmarked) repository.bookmarkArticle(article)
            else repository.removeBookmarkedArticle(article)
            when (result) {
                is Resource.Success ->
                    _onBookmarkChanged.emit(bookmarked)
                is Resource.Failure ->
                    detailUiState.error?.invoke(true, result.message)
                is Resource.Error ->
                    detailUiState.error?.invoke(false, result.message)
            }
        }
    }
}