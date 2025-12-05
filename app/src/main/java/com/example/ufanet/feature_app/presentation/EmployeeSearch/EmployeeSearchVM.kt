package com.example.ufanet.feature_app.presentation.EmployeeSearch

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EmployeeSearchVM: ViewModel() {
    private val _state = mutableStateOf(EmployeeSearchState())
    val state: State<EmployeeSearchState> = _state

    fun onEvent(event: EmployeeSearchEvent){
        when(event){
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
            is EmployeeSearchEvent.SelectedStatus -> {
                _state.value = state.value.copy(
                    selectStatus = true
                )
            }
            is EmployeeSearchEvent.SelectedComments -> {
                _state.value = state.value.copy(
                    selectComments = true
                )
            }
        }
    }
}