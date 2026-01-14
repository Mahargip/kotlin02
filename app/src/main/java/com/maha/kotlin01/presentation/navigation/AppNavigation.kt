package com.maha.kotlin01.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maha.kotlin01.di.AppModule
import com.maha.kotlin01.presentation.detail.DetailScreen
import com.maha.kotlin01.presentation.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{userId}") {
        fun createRoute(userId: String) = "detail/$userId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val homeViewModel = AppModule.provideHomeViewModel()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                onItemClick = { userId ->
                    navController.navigate(Screen.Detail.createRoute(userId))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            DetailScreen(
                userId = userId,
                viewModel = homeViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
