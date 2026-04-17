package com.example.ufanet.feature_app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: Int = 0,
    val application_id: Int = 0,
    val author_id: String = "",
    val comment_text: String = ""
)
