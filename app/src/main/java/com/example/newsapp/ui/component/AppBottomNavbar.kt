package com.example.newsapp.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val iconActive: Int,
    val route: String
)

@Composable
fun AppBottomNavbar(
    items: List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
    isVisible: Boolean = true
) {
    // Current back stack entry
    val backStackEntry = navController.currentBackStackEntryAsState()

    if (isVisible) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.background,
            elevation = 4.dp,
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = {
                        Icon(
                            painter = painterResource(id = if (selected) item.iconActive else item.icon),
                            contentDescription = stringResource(id = item.title),
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.secondary
                )
            }
        }
    }
}