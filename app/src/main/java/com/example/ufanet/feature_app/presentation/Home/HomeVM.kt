package com.example.ufanet.feature_app.presentation.Home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetApplicationsUseCase
import com.example.ufanet.feature_app.domain.usecase.RemoveApplicationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeVM(
    private val getApplicationsUseCase: GetApplicationsUseCase,
    private val removeApplicationUseCase: RemoveApplicationUseCase
): ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.GetApplications -> {
                viewModelScope.launch(Dispatchers.IO){
                    try{
                        _state.value = state.value.copy(
                            application = getApplicationsUseCase.invoke()
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
            is HomeEvent.RemoveApplication -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        removeApplicationUseCase.invoke(event.value)
                        _state.value = state.value.copy(
                            application = getApplicationsUseCase.invoke()
                        )
                    } catch (ex: Exception) {
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
        }
    }
}