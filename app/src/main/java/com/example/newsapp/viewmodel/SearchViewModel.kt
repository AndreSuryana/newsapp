package com.example.newsapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.newsapp.data.model.Article
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    data class SearchUiState(
        var isLoading: Boolean = false,
        var articles: Flow<PagingData<Article>>? = null,
        var error: String? = null
    )

    var searchUiState by mutableStateOf(SearchUiState())

    fun searchArticles(keyword: String) {
        searchUiState.isLoading = true
        viewModelScope.launch {
            searchUiState = searchUiState.copy(
                articles = repository.searchArticles(keyword).cachedIn(viewModelScope)
            )
        }
        searchUiState.isLoading = false
    }
}