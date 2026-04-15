package com.example.ufanet.feature_app.presentation.EmployeeSearch

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetFilterApplicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeSearchVM @Inject constructor(
    private val getFilterApplicationUseCase: GetFilterApplicationUseCase
) : ViewModel() {
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
                        2 -> "address"
                        3 -> "phone"
                        4 -> "description"
                        else -> ""
                    },
                    filterStatus = when (state.value.selectStatus) {
                        0 -> ""
                        1 -> "Не принята"
                        2 -> "Принята"
                        3 -> "Выполнена"
                        else -> ""
                    },
                    filterComments = when (state.value.selectComments) {
                        0 -> -1
                        1 -> 0
                        2 -> 1
                        3 -> 2
                        4 -> 3
                        else -> -1
                    },
                    showFilter = false
                )
                search()
            }
            is EmployeeSearchEvent.Search -> {
                search()
            }
        }
    }
    private fun search(){
        if(state.value.searchText.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _state.value = state.value.copy(
                        applicationsList = getFilterApplicationUseCase(
                            searchText = state.value.searchText,
                            column = state.value.filterSearch,
                            status =  state.value.filterStatus,
                            commentsCount =  state.value.filterComments
                        )
                    )
                    Log.i("filter", state.value.filterStatus)
                } catch (ex: Exception) {
                    Log.e("supabase", ex.message.toString())
                }
            }
        }
    }
}