package com.example.ufanet.feature_app.presentation.SignIn

data class SignInState(
    val email: String = "",
    val password: String = "",
    val visual: Boolean = true,
    val isComplete: Boolean = false,
    val exception: String = "",
    val status: String = ""
)
