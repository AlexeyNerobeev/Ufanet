package com.example.ufanet.feature_app.presentation.EmployeeProfile

data class EmployeeProfileState(
    val id: Int = 0,
    val email: String = "",
    val phone: String = "",
    val companyName: String = "",
    val info: String = "",
    val signOut: Boolean = false,
    val alertSignOut: Boolean = false
)