package com.example.newsapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Bookmark : Screen("bookmark")
    object Detail : Screen("detail")
}
