package com.example.ufanet.feature_app.presentation.Splash

sealed class SplashScreenEvent {
    data object GetCurrentUserId: SplashScreenEvent()
}