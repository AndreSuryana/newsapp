package com.example.newsapp.screen.bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.ui.component.AppHeader
import com.example.newsapp.ui.component.HorizontalArticle
import com.example.newsapp.ui.component.LoadingDialog
import com.example.newsapp.ui.navigation.Screen
import com.example.newsapp.viewmodel.ArticleViewModel
import com.example.newsapp.viewmodel.BookmarkViewModel
import kotlinx.coroutines.launch

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: BookmarkViewModel = hiltViewModel(),
    sharedViewModel: ArticleViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val bookmarkUiState by viewModel.bookmarkUiState.collectAsState()
    val articles = bookmarkUiState.articles

    if (bookmarkUiState.isLoading) {
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
                title = R.string.menu_bookmark,
                textStyle = MaterialTheme.typography.h1,
                textAlign = TextAlign.Start
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
            bookmarkUiState.error?.let { message ->
                scope.launch { snackbarHostState.showSnackbar(message = message) }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(
                items = articles,
                key = { it.articleUrl }
            ) { article ->
                HorizontalArticle(
                    article = article,
                    onItemClick = {
                        sharedViewModel.setArticle(it)
                        navController.navigate(Screen.Detail.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (articles.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.empty_bookmarks),
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }
    }
}