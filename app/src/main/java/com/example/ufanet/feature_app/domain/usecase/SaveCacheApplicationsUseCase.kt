package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class SaveCacheApplicationsUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(applications: List<Application>){
        applicationRepository.saveCacheApplications(applications)
    }
}