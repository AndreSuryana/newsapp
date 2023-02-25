package com.example.newsapp.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.newsapp.R
import com.example.newsapp.ui.component.*
import com.example.newsapp.ui.navigation.Screen
import com.example.newsapp.viewmodel.ArticleViewModel
import com.example.newsapp.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    sharedViewModel: ArticleViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val lifecycle = LocalLifecycleOwner.current

    val explore = viewModel.homeUiState.explore?.collectAsLazyPagingItems()

    if (viewModel.homeUiState.isLoading) {
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
                title = R.string.menu_home,
                textStyle = MaterialTheme.typography.h1,
                textAlign = TextAlign.Start
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
            LaunchedEffect(key1 = viewModel.error) {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.error.collectLatest { message ->
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            // Top Headlines
            stickyHeader {
                Heading(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp),
                    stringRes = R.string.heading_top_headlines
                )
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items = viewModel.homeUiState.headlines) { article ->
                        VerticalArticle(
                            article = article,
                            onItemClick = {
                                sharedViewModel.setArticle(it)
                                navController.navigate(Screen.Detail.route)
                            },
                            modifier = Modifier.width(280.dp)
                        )
                    }
                }
            }

            // Explore
            stickyHeader {
                Heading(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp),
                    stringRes = R.string.heading_explore
                )
            }

            item {
                CategorySelector(
                    modifier = Modifier
                        .fillMaxWidth(),
                    selectedCategory = viewModel.homeUiState.selectedCategory,
                    onCategorySelected = {
                        viewModel.setCategory(it)
                    }
                )
            }

            if (explore != null) {
                itemsIndexed(
                    items = explore,
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

            // Explore - First load
            when (val state = explore?.loadState?.refresh) {
                is LoadState.Loading -> item { LoadingIndicator() }
                is LoadState.Error -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(state.error.message ?: "An error occurred")
                    }
                }
                else -> {}
            }

            // Explore - Pagination load
            when (val state = explore?.loadState?.append) {
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