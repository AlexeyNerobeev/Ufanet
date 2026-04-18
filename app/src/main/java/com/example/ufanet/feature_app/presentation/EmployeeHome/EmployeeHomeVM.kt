package com.example.ufanet.feature_app.presentation.EmployeeHome

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetAllApplicationsUseCase
import com.example.ufanet.feature_app.domain.usecase.GetAllCacheApplicationsUseCase
import com.example.ufanet.feature_app.domain.usecase.SaveCacheApplicationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeHomeVM @Inject constructor(
    private val getAllApplicationsUseCase: GetAllApplicationsUseCase,
    private val saveCacheApplicationsUseCase: SaveCacheApplicationsUseCase,
    private val getAllCacheApplicationsUseCase: GetAllCacheApplicationsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(EmployeeHomeState())
    val state: State<EmployeeHomeState> = _state

    fun onEvent(event: EmployeeHomeEvent) {
        when (event) {
            is EmployeeHomeEvent.GetAllApplications -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val remote = getAllApplicationsUseCase.invoke()
                        saveCacheApplicationsUseCase.invoke(remote)
                        _state.value = state.value.copy(
                            application = getAllCacheApplicationsUseCase.invoke()
                        )
                        Log.i("loadFromServer", "данные загружены с сервера")
                    } catch (ex: Exception) {
                        Log.e("supabase", ex.message.toString())
                        _state.value = state.value.copy(
                            application = getAllCacheApplicationsUseCase.invoke()
                        )
                        Log.i("loadFromLocal", "загружены кэшированные данные")
                    }
                }
            }
        }
    }
}