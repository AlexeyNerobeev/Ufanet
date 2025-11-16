package com.example.ufanet.feature_app.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class Profile(
    val id: Int = 0,
    val company_name: String = "",
    val phone: String = "",
    val email: String = "",
    val status: String = "",
    val user_id: String = "",
    val role_id: Int = 0
)
