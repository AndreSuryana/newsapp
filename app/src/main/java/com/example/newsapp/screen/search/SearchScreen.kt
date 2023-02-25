package com.example.newsapp.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.newsapp.R
import com.example.newsapp.ui.component.*
import com.example.newsapp.ui.navigation.Screen
import com.example.newsapp.viewmodel.ArticleViewModel
import com.example.newsapp.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
    sharedViewModel: ArticleViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val articles = viewModel.searchUiState.articles?.collectAsLazyPagingItems()
    var keyword by rememberSaveable { mutableStateOf("") }

    if (viewModel.searchUiState.isLoading) {
        LoadingDialog()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier,
        topBar = {
            AppHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = R.string.menu_search,
                textStyle = MaterialTheme.typography.h1,
                textAlign = TextAlign.Start
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
            viewModel.searchUiState.error?.let { message ->
                scope.launch { snackbarHostState.showSnackbar(message = message) }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            item {
                SearchField(
                    text = keyword,
                    onTextChanged = {
                        keyword = it
                        viewModel.searchArticles(keyword)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(48.dp)
                )
            }

            // Search Result
            if (articles != null) {
                itemsIndexed(
                    items = articles,
                    key = { _, item -> item.articleUrl }
                ) { _, article ->
                    if (article != null) {
                        HorizontalArticle(
                            article = article,
                            onItemClick = {
                                sharedViewModel.setArticle(it)
                                navController.navigate(Screen.Detail.route)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Search Result - First load
            when (val state = articles?.loadState?.refresh) {
                is LoadState.Loading -> item { LoadingIndicator() }
                is LoadState.Error -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(state.error.message ?: "An error occurred")
                    }
                }
                else -> {}
            }

            // Search Result - Pagination load
            when (val state = articles?.loadState?.append) {
                is LoadState.Loading -> item { LoadingIndicator() }
                is LoadState.Error -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(state.error.message ?: "An error occurred")
                    }
                }
                else -> {}
            }
        }
    }
}