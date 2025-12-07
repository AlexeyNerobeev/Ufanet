package com.example.ufanet.feature_app.presentation.EmployeeSearch

import androidx.compose.ui.text.Placeholder
import com.example.ufanet.feature_app.domain.models.Application

data class EmployeeSearchState (
    val searchText: String = "",
    val showFilter: Boolean = false,
    val placeholder: String = "Название организции...",
    val selectSearch: Int = 1,
    val selectStatus: Int = 0,
    val selectComments: Int = 0,
    val applicationsList: List<Application> = listOf(),
    val filterSearch: String = "company_name",
    val filterStatus: String = "",
    val filterComments: Int = 0
)