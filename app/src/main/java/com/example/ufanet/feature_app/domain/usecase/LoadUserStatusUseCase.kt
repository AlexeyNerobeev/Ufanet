package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.CurrentSessionRepository

class LoadUserStatusUseCase(private val currentSessionRepository: CurrentSessionRepository) {
    operator fun invoke(): String{
        return currentSessionRepository.loadUserStatus()
    }
}