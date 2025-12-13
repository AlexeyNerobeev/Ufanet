package com.example.ufanet.feature_app.presentation.Comments

import com.example.ufanet.feature_app.domain.models.Comment

data class CommentsState(
    val applicationId: Int = 0,
    val currentApplicationStatus: String = "",
    val applicationStatus: String = "",
    val newComment: Boolean = false,
    val commentDescription: String = "",
    val isComplete: Boolean = false,
    val commentsList: List<Comment> = listOf(),
    val progressIndicator: Boolean = false,
    val error: String = "",
    val showNotSelectItems: Boolean = false
)
