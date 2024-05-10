package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationComposable(database: AppDatabase) {
    val navController = rememberNavController()
    val context = LocalContext.current
    var startDestination = if (isUserLoggedIn(context)) Home.route else Onboarding.route
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(Home.route) {
            Home(navController, database)
        }
        composable(Profile.route) {
            Profile(navController)
        }
    }

}

fun isUserLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.contains("email")
}