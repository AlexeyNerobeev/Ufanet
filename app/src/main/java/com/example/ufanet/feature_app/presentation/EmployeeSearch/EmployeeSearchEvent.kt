package com.example.ufanet.feature_app.presentation.EmployeeSearch

import com.example.ufanet.feature_app.presentation.EmployeeHome.EmployeeHomeEvent

sealed class EmployeeSearchEvent {
    data class EnteredSearchText(val value: String): EmployeeSearchEvent()
    data object ShowFilter: EmployeeSearchEvent()
    data class UpdatePlaceholder(val value: String): EmployeeSearchEvent()
    data class SelectedSearch(val value: Int): EmployeeSearchEvent()
    data class SelectedStatus(val value: Int): EmployeeSearchEvent()
    data class SelectedComments(val value: Int): EmployeeSearchEvent()
    data object ApplyFilters: EmployeeSearchEvent()
    data object Search: EmployeeSearchEvent()
}