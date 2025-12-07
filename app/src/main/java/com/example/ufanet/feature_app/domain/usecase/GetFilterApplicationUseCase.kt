package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class GetFilterApplicationUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(searchText: String, column: String, status: String, commentsCount: Int): List<Application>{
        return applicationRepository.getFilterApplication(searchText = searchText,
            column = column,
            status = status,
            commentsCount = commentsCount)
    }
}