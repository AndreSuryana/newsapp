package com.example.newsapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import androidx.compose.runtime.*
import androidx.paging.cachedIn
import com.example.newsapp.util.NewsCategory
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    data class HomeUiState(
        var isLoading: Boolean = false,
        var headlines: List<Article> = emptyList(),
        var explore: Flow<PagingData<Article>>? = null,
        var selectedCategory: NewsCategory = NewsCategory.values().first()
    )

    var homeUiState by mutableStateOf(HomeUiState())

    private var _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()


    init {
        loadArticles()
    }

    private fun loadArticles() {
        setLoadingState(true)
        viewModelScope.launch {
            homeUiState = homeUiState.copy(
                headlines = getTopHeadlines(),
                explore = repository.getExploreArticles(homeUiState.selectedCategory)
                    .cachedIn(viewModelScope)
            )
            setLoadingState(false)
        }
    }

    private fun getTopHeadlines(): List<Article> = runBlocking {
        when (val result = repository.getTopHeadlines()) {
            is Resource.Success -> result.data.articles
            is Resource.Failure -> {
                _error.emit(result.message!!)
                emptyList()
            }
            is Resource.Error -> {
                _error.emit(result.message!!)
                emptyList()
            }
        }
    }

    fun setCategory(category: NewsCategory) {
        viewModelScope.launch {
            homeUiState = homeUiState.copy(
                selectedCategory = category,
                explore = repository.getExploreArticles(category)
            )
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        homeUiState = homeUiState.copy(
            isLoading = isLoading
        )
    }
}