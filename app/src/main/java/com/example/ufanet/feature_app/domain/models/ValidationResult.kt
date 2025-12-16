package com.example.ufanet.feature_app.domain.models

data class ValidationResult(
    val emailError: String = "",
    val passwordError: String = "",
    val isValid: Boolean = false
)
