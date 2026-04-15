package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.CurrentSessionRepository

class SaveUserIdUseCase(private val currentSessionRepository: CurrentSessionRepository) {
    operator fun invoke(id: String, status: String){
        currentSessionRepository.saveUser(id, status)
    }
}