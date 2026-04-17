package com.example.ufanet.feature_app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String? = "",
    val email: String? = ""
)