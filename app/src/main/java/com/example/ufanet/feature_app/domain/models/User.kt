package com.example.ufanet.feature_app.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = "",
    val email: String? = ""
)
