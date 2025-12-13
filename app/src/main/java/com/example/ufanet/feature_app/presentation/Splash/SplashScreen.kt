package com.example.ufanet.feature_app.presentation.Splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.ufanet.NavRoutes
import com.example.ufanet.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(navController: NavController, vm: SplashScreenVM = koinViewModel()) {
    val state = vm.state.value
    LaunchedEffect(key1 = !state.goHome, key2 = !state.goSignIn) {
        vm.onEvent(SplashScreenEvent.GetCurrentUserId)
        if (state.goHome) {
            if (state.status == "клиент") {
                navController.navigate(NavRoutes.HomeScreen.route){
                    popUpTo(0){
                        inclusive = true
                    }
                }
            } else if (state.status == "сотрудник") {
                navController.navigate(NavRoutes.EmployeeHomeScreen.route){
                    popUpTo(0){
                        inclusive = true
                    }
                }
            }
        } else if (state.goSignIn) {
            navController.navigate(NavRoutes.SignInScreen.route){
                popUpTo(0){
                    inclusive = true
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.splash_icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}