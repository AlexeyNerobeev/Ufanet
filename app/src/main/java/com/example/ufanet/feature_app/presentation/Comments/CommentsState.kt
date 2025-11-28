package com.example.ufanet.feature_app.presentation.Comments

data class CommentsState(
    val applicationStatus: String = "",
    val newComment: Boolean = false,
    val commentDescription: String = "",
    val isComplete: Boolean = false,
    val commentsList: List<String> = listOf()
)
