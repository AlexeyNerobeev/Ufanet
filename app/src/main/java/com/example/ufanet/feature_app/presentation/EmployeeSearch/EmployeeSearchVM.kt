package com.example.ufanet.feature_app.presentation.EmployeeSearch

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeSearchVM : ViewModel() {
    private val _state = mutableStateOf(EmployeeSearchState())
    val state: State<EmployeeSearchState> = _state

    fun onEvent(event: EmployeeSearchEvent) {
        when (event) {
            is EmployeeSearchEvent.EnteredSearchText -> {
                _state.value = state.value.copy(
                    searchText = event.value
                )
            }

            is EmployeeSearchEvent.ShowFilter -> {
                _state.value = state.value.copy(
                    showFilter = !state.value.showFilter
                )
            }

            is EmployeeSearchEvent.UpdatePlaceholder -> {
                _state.value = state.value.copy(
                    placeholder = event.value
                )
            }

            is EmployeeSearchEvent.SelectedSearch -> {
                _state.value = state.value.copy(
                    selectSearch = event.value
                )
            }

            is EmployeeSearchEvent.SelectedStatus -> {
                _state.value = state.value.copy(
                    selectStatus = event.value
                )
            }

            is EmployeeSearchEvent.SelectedComments -> {
                _state.value = state.value.copy(
                    selectComments = event.value
                )
            }

            is EmployeeSearchEvent.ApplyFilters -> {
                _state.value = state.value.copy(
                    placeholder = when (state.value.selectSearch) {
                        1 -> "Название организации..."
                        2 -> "Адрес..."
                        3 -> "Телефон..."
                        4 -> "Описание проблемы..."
                        else -> "Название организации..."
                    },
                    filterSearch = when (state.value.selectSearch) {
                        1 -> "company_name"
                        2 -> "adress"
                        3 -> "phone"
                        4 -> "description"
                        else -> ""
                    },
                    filterStatus = when (state.value.selectStatus) {
                        1 -> "Не принята"
                        2 -> "Принята"
                        3 -> "Выполнена"
                        else -> ""
                    },
                    filterComments = when (state.value.selectComments) {
                        1 -> "Нет"
                        2 -> "Один"
                        3 -> "Два"
                        4 -> "Больше двух"
                        else -> ""
                    },
                    showFilter = false
                )
            }

            is EmployeeSearchEvent.Search -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (state.value.filterStatus.isNotEmpty()){

                    } else{

                    }
                }
            }
        }
    }
}