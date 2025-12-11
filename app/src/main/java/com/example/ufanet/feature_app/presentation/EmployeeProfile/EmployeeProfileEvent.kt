package com.example.ufanet.feature_app.presentation.EmployeeProfile

sealed class EmployeeProfileEvent {
    data class EnteredCompanyName(val value: String): EmployeeProfileEvent()
    data class EnteredPhone(val value: String): EmployeeProfileEvent()
    data object UpdateProfile: EmployeeProfileEvent()
    data object GetProfile: EmployeeProfileEvent()
    data object ClearInfo: EmployeeProfileEvent()
    data object SignOut: EmployeeProfileEvent()
    data object ShowAlertSignOut: EmployeeProfileEvent()
}