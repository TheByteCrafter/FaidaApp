package com.technologiesiline.faidaapp.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.filled.BarChart

@Composable
fun FaidaBottomNav(navController: NavHostController) {

    val items = listOf(
        "Home" to Icons.Default.Home,
        "History" to Icons.Default.History,
        "Insights" to Icons.Default.BarChart
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Color.White.copy(alpha = 0.1f),
    ) {

        items.forEach { (screen, icon) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = screen,
                       tint = if (currentDestination?.hierarchy?.any { it.route == screen } == true)
                            Color(0xFF00F2FE) else Color.White.copy(alpha = 0.6f)
                    )
                },
                label = { Text(screen) },
                selected = currentDestination?.hierarchy?.any { it.route == screen } == true,
                onClick = {
                    val hasGraph = try { navController.graph; true } catch (e: Exception) { false }

                    if (hasGraph) {
                        navController.navigate(screen) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        println("DEBUG: NavController has no graph set!")
                    }
                }
            )
        }
    }
}