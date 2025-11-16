package com.example.ufanet.feature_app.presentation.EmployeeHome

import com.example.ufanet.feature_app.domain.models.Application

data class EmployeeHomeState(
    val application: List<Application> = listOf()
)