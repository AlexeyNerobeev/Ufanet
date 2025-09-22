package com.example.ufanet.feature_app.presentation.Home

sealed class HomeEvent {
    data object GetApplications: HomeEvent()
    data class RemoveApplication(val value: Int): HomeEvent()
}