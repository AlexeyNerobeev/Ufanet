package com.example.ufanet.feature_app.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Application(
    val id: Int = 0,
    val company_name: String = "",
    val phone: String = "",
    val description: String = "",
    val user_id: String = "",
    val address: String = "",
    val status: String = ""
    )