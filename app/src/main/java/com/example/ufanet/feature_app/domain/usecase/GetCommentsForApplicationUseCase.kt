package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.Comment
import com.example.ufanet.feature_app.domain.repository.CommentRepository

class GetCommentsForApplicationUseCase(private val commentsRepository: CommentRepository) {
    suspend operator fun invoke(applicationId: Int): List<Comment>{
        return commentsRepository.getComments(applicationId)
    }
}