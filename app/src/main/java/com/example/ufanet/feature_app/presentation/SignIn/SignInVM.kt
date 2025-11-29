package com.example.ufanet.feature_app.presentation.SignIn

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetProfileStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.SignInUseCase
import io.ktor.websocket.FrameParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInVM(
    private val signInUseCase: SignInUseCase,
    private val getProfileStatusUseCase: GetProfileStatusUseCase
): ViewModel() {
    private val _state = mutableStateOf(SignInState())
    val state: State<SignInState> = _state

    fun onEvent(event: SignInEvent){
        when(event){
            is SignInEvent.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = event.value
                )
            }
            is SignInEvent.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.value
                )
            }
            is SignInEvent.SignIn -> {
                viewModelScope.launch(Dispatchers.IO){
                    try {
                        signInUseCase.invoke(state.value.email, state.value.password)
                        delay(1000)
                        _state.value = state.value.copy(
                            status = getProfileStatusUseCase.invoke().status
                        )
                        _state.value = state.value.copy(
                            isComplete = true
                        )
                    } catch (ex: Exception){
                        _state.value = state.value.copy(
                            progressIndicator = false,
                            exception = ex.message.toString()
                        )
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
            is SignInEvent.VisualTransformation -> {
                _state.value = state.value.copy(
                    visual = !state.value.visual
                )
            }
            is SignInEvent.ExceptionClear -> {
                _state.value = state.value.copy(
                    exception = ""
                )
            }
            is SignInEvent.ShowProgressIndicator -> {
                _state.value = state.value.copy(
                    progressIndicator = true
                )
            }
        }
    }
}