package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationComposable() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Onboarding.route) {
        composable(Onboarding.route) {
            Onboarding()
        }
        composable(Home.route) {
            Home()
        }
        composable(Profile.route) {
            Profile()
        }
    }

}