package com.example.littlelemon

interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route = "home"
}
object Profile : Destinations {
    override val route = "profile"
}

object Onboarding : Destinations {
    override val route = "onboarding"
}