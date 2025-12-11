package com.example.ufanet.feature_app.presentation.Splash

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetCurrentUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.GetProfileStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenVM(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getProfileStatusUseCase: GetProfileStatusUseCase
): ViewModel() {
    private val _state = mutableStateOf(SplashScreenState())
    val state: State<SplashScreenState> = _state

    fun onEvent(event: SplashScreenEvent){
        when(event){
            is SplashScreenEvent.GetCurrentUserId -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        _state.value = state.value.copy(
                            currentUserId = getCurrentUserIdUseCase.invoke().id
                        )
                        if(state.value.currentUserId != null){
                            _state.value = state.value.copy(
                                status = getProfileStatusUseCase.invoke().status,
                                goHome = true
                            )
                        } else{
                            _state.value = state.value.copy(
                                goSignIn = true
                            )
                        }
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
        }
    }
}