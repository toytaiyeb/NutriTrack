package com.example.taiyebmustufa34377190.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.taiyebmustufa34377190.screens.*

sealed class Screens(val route: String) {
    object Welcome : Screens("welcome_screen")
    object Login : Screens("login_screen")
    object FoodIntake : Screens("food_intake_screen")
    object Home : Screens("home_screen")
    object Insights : Screens("insights_screen")

    fun createRouteHs(phoneNumber: String, userId: String): String {
        return "home_screen/$phoneNumber/$userId"
    }
    fun createRouteIs(phoneNumber: String, userId: String): String {
        return "insights_screen/$phoneNumber/$userId"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.Welcome.route) {
        composable(Screens.Welcome.route) { WelcomeScreen(navController) }
        composable(Screens.Login.route) { LoginScreen(navController) }

        composable(Screens.FoodIntake.route) {
            FoodIntakeScreen(navController)
        }

        composable(
            route = Screens.Home.route + "/{phoneNumber}/{userId}",
            arguments = listOf(
                navArgument("phoneNumber") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            HomeScreen(navController = navController, phoneNumber = phoneNumber, userId = userId)
        }
        composable(
            route = Screens.Insights.route + "/{phoneNumber}/{userId}",
            arguments = listOf(
                navArgument("phoneNumber") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            InsightsScreen(navController = navController, phoneNumber = phoneNumber, userId = userId)
        }



    }
}
