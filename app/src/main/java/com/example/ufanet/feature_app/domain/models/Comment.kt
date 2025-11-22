package com.example.ufanet.feature_app.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Comment (
    val id: Int = 0,
    val application_id: Int = 0,
    val author_id: String = "",
    val comment_text: String = ""
)