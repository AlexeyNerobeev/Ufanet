package com.example.ufanet

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ufanet.feature_app.presentation.Applications.ApplicationsScreen
import com.example.ufanet.feature_app.presentation.EmployeeHome.EmployeeHomeScreen
import com.example.ufanet.feature_app.presentation.EmployeeProfile.EmployeeProfileScreen
import com.example.ufanet.feature_app.presentation.EmployeeSearch.EmployeeSearchScreen
import com.example.ufanet.feature_app.presentation.Home.HomeScreen
import com.example.ufanet.feature_app.presentation.Profile.ProfileScreen
import com.example.ufanet.feature_app.presentation.SignIn.SignInScreen
import com.example.ufanet.feature_app.presentation.SignUp.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = NavRoutes.SignInScreen.route){
                composable (NavRoutes.SignInScreen.route) { SignInScreen(navController) }
                composable (NavRoutes.SignUpScreen.route) { SignUpScreen(navController)}
                composable (NavRoutes.HomeScreen.route) { HomeScreen(navController)}
                composable (route = NavRoutes.ApplicationsScreen.route,
                    arguments = listOf(navArgument("itemId") {
                        type = NavType.IntType
                    })) { backStackEntry ->
                    val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
                    ApplicationsScreen(itemId = itemId, navController = navController)
                }
                composable (NavRoutes.ProfileScreen.route) { ProfileScreen(navController) }
                composable(NavRoutes.EmployeeHomeScreen.route) { EmployeeHomeScreen(navController) }
                composable(NavRoutes.EmployeeSearchScreen.route) { EmployeeSearchScreen(navController) }
                composable(NavRoutes.EmployeeProfileScreen.route) { EmployeeProfileScreen(navController) }
            }
        }
    }
}