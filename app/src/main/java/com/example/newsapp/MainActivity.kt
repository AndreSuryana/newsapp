package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.component.AppBottomNavbar
import com.example.newsapp.ui.navigation.AppNavigation
import com.example.newsapp.ui.navigation.Screen
import com.example.newsapp.ui.navigation.menu.BottomNavMenu
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsAppTheme {

                // Get navigation controller
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()

                var bottomNavState by remember {
                    mutableStateOf(true)
                }

                backStackEntry?.destination?.route?.let {
                    Log.d("MainActivity", "${backStackEntry?.destination?.route}")
                    bottomNavState = it != Screen.Detail.route
                }

                Scaffold(
                    bottomBar = {
                        AppBottomNavbar(
                            items = BottomNavMenu.MENU,
                            navController = navController,
                            onItemClick = { item ->
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            isVisible = bottomNavState
                        )
                    }
                ) { paddingValues ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
}