package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class RemoveApplicationUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(id: Int){
        applicationRepository.removeApplication(id)
    }
}