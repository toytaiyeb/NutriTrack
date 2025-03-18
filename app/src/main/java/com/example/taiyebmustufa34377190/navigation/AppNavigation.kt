package com.example.taiyebmustufa34377190.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taiyebmustufa34377190.screens.LoginScreen
import com.example.taiyebmustufa34377190.screens.FoodIntakeScreen
import com.example.taiyebmustufa34377190.screens.WelcomeScreen

sealed class Screens(val route: String) {
    object Welcome : Screens("welcome_screen")
    object Login : Screens("login_screen")
    object FoodIntake : Screens("food_intake_screen")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.Welcome.route) {
        composable(Screens.Welcome.route) { WelcomeScreen(navController) }
        composable(Screens.Login.route) { LoginScreen(navController) }
        composable(Screens.FoodIntake.route) { FoodIntakeScreen(navController) }
    }
}
