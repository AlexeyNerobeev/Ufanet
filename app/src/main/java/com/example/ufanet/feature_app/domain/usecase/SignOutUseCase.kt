package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.AuthRepository

class SignOutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(){
        authRepository.signOut()
    }
}