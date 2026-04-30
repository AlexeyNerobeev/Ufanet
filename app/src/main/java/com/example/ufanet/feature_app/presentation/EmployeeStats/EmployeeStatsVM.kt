package com.example.ufanet.feature_app.presentation.EmployeeStats

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetEmployeeStatsUseCase
import com.example.ufanet.feature_app.domain.usecase.LoadUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

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
                    try {
                        val userId = loadUserIdUseCase.invoke()

                        val stats = getEmployeeStatsUseCase.invoke(userId, null, null)

                        _state.value = state.value.copy(
                            stats = stats
                        )
                    } catch (e: Exception) {
                        Log.e("supabase", e.message.toString())
                    }
                }
            }
        }
    }

    fun loadStatsByDate(from: Long, to: Long) {
        viewModelScope.launch(Dispatchers.IO) {

            val userId = loadUserIdUseCase.invoke()

            val fromDate = Instant.ofEpochMilli(from)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            val toDate = Instant.ofEpochMilli(to)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            val stats = getEmployeeStatsUseCase.invoke(
                userId,
                fromDate.toString(),
                toDate.toString()
            )

            _state.value = state.value.copy(
                stats = stats,
                fromDate = from,
                toDate = to
            )
        }
    }
}