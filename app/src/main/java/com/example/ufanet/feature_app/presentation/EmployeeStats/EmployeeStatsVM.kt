package com.example.ufanet.feature_app.presentation.EmployeeStats

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetCurrentUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.GetEmployeeStatsUseCase
import com.example.ufanet.feature_app.domain.usecase.LoadUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class EmployeeStatsVM @Inject constructor(
    private val getEmployeeStatsUseCase: GetEmployeeStatsUseCase,
    private val loadUserIdUseCase: LoadUserIdUseCase
) : ViewModel() {

    private val _state = mutableStateOf(EmployeeStatsState())
    val state: State<EmployeeStatsState> = _state

    fun onEvent(event: EmployeeStatsEvent) {
        when (event) {

            is EmployeeStatsEvent.LoadStats -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val userId = loadUserIdUseCase.invoke()

                    val stats = getEmployeeStatsUseCase.invoke(userId)

                    _state.value = state.value.copy(
                        stats = stats
                    )
                }
            }
        }
    }
}