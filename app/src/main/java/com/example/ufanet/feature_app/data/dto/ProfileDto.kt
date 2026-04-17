package com.example.ufanet.feature_app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: Int = 0,
    val company_name: String = "",
    val phone: String = "",
    val email: String = "",
    val status: String = "",
    val user_id: String = "",
    val role_id: Int = 0
)
