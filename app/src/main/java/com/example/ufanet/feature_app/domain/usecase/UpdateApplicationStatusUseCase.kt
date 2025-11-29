package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class UpdateApplicationStatusUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(applicationId: Int, status: String){
        applicationRepository.updateApplicationStatus(applicationId, status)
    }
}