package com.example.ufanet.feature_app.presentation.EmployeeStats

sealed class EmployeeStatsEvent {
    data object LoadStats : EmployeeStatsEvent()
}