package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.CommentRepository

class AddCommentUseCase(private val commentRepository: CommentRepository) {
    suspend operator fun invoke(commentText: String, applicationId: Int){
        commentRepository.addComment(commentText = commentText, applicationId = applicationId)
    }
}