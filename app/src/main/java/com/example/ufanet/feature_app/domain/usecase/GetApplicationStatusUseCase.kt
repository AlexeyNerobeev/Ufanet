package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class GetApplicationStatusUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(applicationId: Int): Application{
        return applicationRepository.getApplicationStatus(applicationId)
    }
}