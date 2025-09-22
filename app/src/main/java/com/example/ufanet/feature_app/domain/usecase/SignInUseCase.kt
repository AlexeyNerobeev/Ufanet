package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String){
        authRepository.signIn(email, password)
    }
}