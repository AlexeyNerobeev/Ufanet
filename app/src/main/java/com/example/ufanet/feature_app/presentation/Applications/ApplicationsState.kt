package com.example.ufanet.feature_app.presentation.Applications

data class ApplicationsState(
    val id: Int = 0,
    val companyName: String = "",
    val address: String = "",
    val phone: String = "",
    val description: String = "",
    val isComplete: Boolean = false,
    val error: String = ""
)