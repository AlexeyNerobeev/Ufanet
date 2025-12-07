package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class UpdateApplicationCommentsCountUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(applicationId: Int, commentsCount: Int){
        applicationRepository.updateCommentsCount(applicationId = applicationId, commentsCount = commentsCount)
    }
}