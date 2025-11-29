package com.example.ufanet.feature_app.presentation.SignUp

data class SignUpState(
    val companyName: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val visual: Boolean = true,
    val isComplete: Boolean = false,
    val exception: String = "",
    val progressIndicator: Boolean = false
)
