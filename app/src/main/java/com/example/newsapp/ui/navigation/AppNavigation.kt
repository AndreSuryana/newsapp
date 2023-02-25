package com.example.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.screen.bookmark.BookmarkScreen
import com.example.newsapp.screen.detail.DetailScreen
import com.example.newsapp.screen.home.HomeScreen
import com.example.newsapp.screen.search.SearchScreen
import com.example.newsapp.viewmodel.ArticleViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Shared view model
    val sharedViewModel = viewModel<ArticleViewModel>()

    // Define navigation route
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        // Home
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, modifier = modifier, sharedViewModel = sharedViewModel)
        }
        // Search
        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController, modifier = modifier, sharedViewModel = sharedViewModel)
        }
        // Bookmark
        composable(route = Screen.Bookmark.route) {
            BookmarkScreen(navController = navController, modifier = modifier, sharedViewModel = sharedViewModel)
        }
        // Detail
        composable(route = Screen.Detail.route) {
            DetailScreen(navController = navController, modifier = modifier, sharedViewModel = sharedViewModel)
        }
    }
}