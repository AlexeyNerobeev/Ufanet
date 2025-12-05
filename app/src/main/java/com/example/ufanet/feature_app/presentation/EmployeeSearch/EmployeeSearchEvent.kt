package com.example.ufanet.feature_app.presentation.EmployeeSearch

sealed class EmployeeSearchEvent {
    data class EnteredSearchText(val value: String): EmployeeSearchEvent()
    data object ShowFilter: EmployeeSearchEvent()
}