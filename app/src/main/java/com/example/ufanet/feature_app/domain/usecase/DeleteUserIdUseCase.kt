package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.CurrentSessionRepository

class DeleteUserIdUseCase(private val currentSessionRepository: CurrentSessionRepository) {
    operator fun invoke(){
        currentSessionRepository.deleteUserId()
    }
}