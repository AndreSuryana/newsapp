package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article = _article.asStateFlow()

    fun setArticle(article: Article) {
        viewModelScope.launch {
            _article.emit(article)
        }
    }
}