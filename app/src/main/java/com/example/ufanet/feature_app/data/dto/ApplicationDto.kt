package com.example.ufanet.feature_app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationDto(
    val id: Int = 0,
    val company_name: String = "",
    val phone: String = "",
    val description: String = "",
    val user_id: String = "",
    val address: String = "",
    val status: String = "",
    val comments_count: Int = 0
)