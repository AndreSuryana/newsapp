package com.example.newsapp.ui.navigation.menu

import com.example.newsapp.R
import com.example.newsapp.ui.component.BottomNavItem
import com.example.newsapp.ui.navigation.Screen

object BottomNavMenu {
    val MENU = listOf(
        BottomNavItem(
            title = R.string.menu_home,
            icon = R.drawable.ic_home,
            iconActive = R.drawable.ic_home_active,
            route = Screen.Home.route
        ),
        BottomNavItem(
            title = R.string.menu_search,
            icon = R.drawable.ic_search,
            iconActive = R.drawable.ic_search_active,
            route = Screen.Search.route
        ),
        BottomNavItem(
            title = R.string.menu_bookmark,
            icon = R.drawable.ic_bookmarks,
            iconActive = R.drawable.ic_bookmarks_active,
            route = Screen.Bookmark.route
        )
    )
}