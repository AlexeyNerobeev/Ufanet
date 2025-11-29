package com.example.ufanet.feature_app.domain.repository

import com.example.ufanet.feature_app.domain.models.Comment

interface CommentRepository {
    suspend fun addComment(commentText: String, applicationId: Int)
    suspend fun getComments(applicationId: Int): List<Comment>
}