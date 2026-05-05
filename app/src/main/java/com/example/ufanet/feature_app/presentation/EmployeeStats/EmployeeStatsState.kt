package com.example.ufanet.feature_app.presentation.EmployeeStats

import com.example.ufanet.feature_app.domain.models.EmployeeStats

data class EmployeeStatsState(
    val stats: EmployeeStats = EmployeeStats(),
    val fromDate: Long? = null,
    val toDate: Long? = null,
    val isLoading: Boolean = true
)