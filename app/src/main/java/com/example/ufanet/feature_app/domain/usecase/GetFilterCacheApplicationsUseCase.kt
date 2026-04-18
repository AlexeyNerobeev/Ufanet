package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class GetFilterCacheApplicationsUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(searchText: String,
                                status: String,
                                commentsCount: Int,
                                column: String): List<Application>{
        return applicationRepository.getFilterCacheApplications(
            searchText = searchText,
            status = status,
            commentsCount = commentsCount,
            column = column
        )
    }
}