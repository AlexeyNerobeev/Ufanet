package com.example.ufanet.feature_app.domain.repository

import com.example.ufanet.feature_app.domain.models.Comment

interface CommentRepository {
    suspend fun addComment(commentText: String)
    suspend fun getComments(applicationId: Int): List<String>
}