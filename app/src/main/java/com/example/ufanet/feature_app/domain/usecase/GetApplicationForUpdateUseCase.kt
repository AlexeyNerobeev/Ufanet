package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository
import io.ktor.http.ContentType

class GetApplicationForUpdateUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(id: Int): Application {
        return applicationRepository.getApplicationForUpdate(id)
    }
}