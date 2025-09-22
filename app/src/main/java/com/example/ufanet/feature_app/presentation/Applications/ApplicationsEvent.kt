package com.example.ufanet.feature_app.presentation.Applications

sealed class ApplicationsEvent {
    data class EnteredCompanyName(val value: String): ApplicationsEvent()
    data class EnteredAddress(val value: String): ApplicationsEvent()
    data class EnteredPhone(val value: String): ApplicationsEvent()
    data class EnteredDescription(val value: String): ApplicationsEvent()
    data object SaveApplication: ApplicationsEvent()
    data object ExceptionClear: ApplicationsEvent()
    data class GetApplicationForUpdate(val value: Int): ApplicationsEvent()
    data object UpdateApplication: ApplicationsEvent()
}