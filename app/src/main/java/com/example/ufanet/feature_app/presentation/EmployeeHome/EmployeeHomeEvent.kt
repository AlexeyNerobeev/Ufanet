package com.example.ufanet.feature_app.presentation.EmployeeHome

sealed class EmployeeHomeEvent {
    data object GetAllApplications: EmployeeHomeEvent()
}