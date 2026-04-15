package com.example.ufanet.feature_app.presentation.Splash

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetCurrentUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.GetProfileStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.LoadUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.LoadUserStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenVM @Inject constructor(
    private val loadUserIdUseCase: LoadUserIdUseCase,
    private val loadUserStatusUseCase: LoadUserStatusUseCase
): ViewModel() {
    private val _state = mutableStateOf(SplashScreenState())
    val state: State<SplashScreenState> = _state

    fun onEvent(event: SplashScreenEvent){
        when(event){
            is SplashScreenEvent.GetCurrentUserId -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val id = loadUserIdUseCase.invoke()
                        val status = loadUserStatusUseCase.invoke()
                        if(id.isNotEmpty() && status.isNotEmpty()){
                            _state.value = state.value.copy(
                                status = status,
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