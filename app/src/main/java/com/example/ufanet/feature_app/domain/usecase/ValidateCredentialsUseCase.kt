package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.ValidationResult

class ValidateCredentialsUseCase {
    operator fun invoke(email: String, password: String): ValidationResult {
        val emailError = if (!isValidEmail(email)) "Некорректный формат email" else ""
        val passwordError = if (password.length < 6) "Пароль должен содержать минимум 6 символов" else ""

        return ValidationResult(
            emailError = emailError,
            passwordError = passwordError,
            isValid = emailError == "" && passwordError == ""
        )
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return emailRegex.matches(email)
    }
}