package com.example.ufanet.feature_app.presentation.Profile

import com.example.ufanet.feature_app.presentation.EmployeeProfile.EmployeeProfileEvent

sealed class ProfileEvent {
    data class EnteredCompanyName(val value: String): ProfileEvent()
    data class EnteredPhone(val value: String): ProfileEvent()
    data object UpdateProfile: ProfileEvent()
    data object GetProfile: ProfileEvent()
    data object ClearInfo: ProfileEvent()
    data object SignOut: ProfileEvent()
    data object ShowAlertSignOut: ProfileEvent()
}