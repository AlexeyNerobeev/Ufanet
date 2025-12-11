package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.User
import com.example.ufanet.feature_app.domain.repository.AuthRepository

class GetCurrentUserIdUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): User{
        return authRepository.getCurrentUserId()
    }
}