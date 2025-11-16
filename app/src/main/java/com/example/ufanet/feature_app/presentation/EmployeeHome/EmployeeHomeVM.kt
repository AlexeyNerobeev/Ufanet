package com.example.ufanet.feature_app.presentation.EmployeeHome

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetAllApplicationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeHomeVM(
    private val getAllApplicationsUseCase: GetAllApplicationsUseCase
): ViewModel() {
    private val _state = mutableStateOf(EmployeeHomeState())
    val state: State<EmployeeHomeState> = _state

    fun onEvent(event: EmployeeHomeEvent){
        when(event){
            is EmployeeHomeEvent.GetAllApplications -> {
                viewModelScope.launch(Dispatchers.IO){
                    try{
                        _state.value = state.value.copy(
                            application = getAllApplicationsUseCase.invoke()
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
        }
    }
}