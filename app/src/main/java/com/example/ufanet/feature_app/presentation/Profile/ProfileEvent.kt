package com.example.ufanet.feature_app.presentation.Profile

sealed class ProfileEvent {
    data class EnteredCompanyName(val value: String): ProfileEvent()
    data class EnteredPhone(val value: String): ProfileEvent()
    data object UpdateProfile: ProfileEvent()
    data object GetProfile: ProfileEvent()
    data object ClearInfo: ProfileEvent()
}