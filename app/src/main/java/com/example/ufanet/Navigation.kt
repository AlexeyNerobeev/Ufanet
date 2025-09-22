package com.example.ufanet

sealed class NavRoutes(val route: String) {
    data object SignInScreen: NavRoutes("SignInScreen")
    data object SignUpScreen: NavRoutes("SignUpScreen")
    data object HomeScreen: NavRoutes("HomeScreen")
    data object ApplicationsScreen: NavRoutes("ApplicationsScreen?itemId={itemId}"){
        fun createRoute(itemId: Int) = "ApplicationsScreen?itemId=$itemId"
    }
    data object ProfileScreen: NavRoutes("ProfileScreen")
    data object EmployeeHomeScreen: NavRoutes("EmployeeHomeScreen")
}