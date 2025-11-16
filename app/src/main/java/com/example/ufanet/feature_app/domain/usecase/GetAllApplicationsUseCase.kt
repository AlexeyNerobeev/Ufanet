package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class GetAllApplicationsUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(): List<Application>{
        return applicationRepository.getAllApplications()
    }
}