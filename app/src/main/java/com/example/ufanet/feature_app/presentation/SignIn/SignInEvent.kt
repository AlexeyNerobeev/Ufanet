package com.example.ufanet.feature_app.presentation.SignIn

sealed class SignInEvent {
    data class EnteredEmail(val value: String): SignInEvent()
    data class EnteredPassword(val value: String): SignInEvent()
    data object SignIn: SignInEvent()
    data object VisualTransformation: SignInEvent()
    data object ExceptionClear: SignInEvent()
    data object ShowProgressIndicator: SignInEvent()
}