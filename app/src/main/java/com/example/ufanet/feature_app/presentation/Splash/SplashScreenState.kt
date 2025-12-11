package com.example.ufanet.feature_app.presentation.Splash

data class SplashScreenState(
    val currentUserId: String? = "",
    val status: String = "",
    val goHome: Boolean = false,
    val goSignIn: Boolean = false
)