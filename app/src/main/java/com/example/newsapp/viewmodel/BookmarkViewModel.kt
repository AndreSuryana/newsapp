package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Article
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    data class BookmarkUiState(
        var isLoading: Boolean = false,
        var articles: List<Article> = emptyList(),
        var error: String? = null
    )

    private var _bookmarkUiState = MutableStateFlow(BookmarkUiState())
    val bookmarkUiState = _bookmarkUiState.asStateFlow()

    init {
        getBookmarkedArticles()
    }

    private fun getBookmarkedArticles() {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getBookmarkedArticles()) {
                is Resource.Success -> _bookmarkUiState.value = _bookmarkUiState.value.copy(
                    isLoading = false,
                    articles = result.data
                )
                is Resource.Failure -> {
                    _bookmarkUiState.value = _bookmarkUiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Error -> {
                    _bookmarkUiState.value = _bookmarkUiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    private fun setLoadingState() {
        _bookmarkUiState.value = _bookmarkUiState.value.copy(
            isLoading = true
        )
    }
}