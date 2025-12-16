package com.example.ufanet.feature_app.presentation.SignUp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.AddProfileUseCase
import com.example.ufanet.feature_app.domain.usecase.SignUpUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateProfileUseCase
import com.example.ufanet.feature_app.domain.usecase.ValidateCredentialsUseCase
import com.example.ufanet.feature_app.presentation.Applications.ApplicationsEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpVM(
    private val signUpUseCase: SignUpUseCase,
    private val addProfileUseCase: AddProfileUseCase,
    private val validateCredentialsUseCase: ValidateCredentialsUseCase
): ViewModel(){
    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    fun onEvent(event: SignUpEvent){
        when(event){
            is SignUpEvent.EnteredCompanyName ->{
                _state.value = state.value.copy(
                    companyName = event.value
                )
            }
            is SignUpEvent.EnteredPhone -> {
                _state.value = state.value.copy(
                    phone = event.value
                )
            }
            is SignUpEvent.EnteredEmail ->{
                _state.value = state.value.copy(
                    email = event.value
                )
            }
            is SignUpEvent.EnteredPassword ->{
                _state.value = state.value.copy(
                    password = event.value
                )
            }
            is SignUpEvent.Registration -> {
                val validationResult = validateCredentialsUseCase.invoke(
                    state.value.email.trim(),
                    state.value.password
                )
                if (!validationResult.isValid) {
                    _state.value = state.value.copy(
                        emailError = validationResult.emailError,
                        passwordError = validationResult.passwordError,
                        progressIndicator = false
                    )
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            if (state.value.companyName.isNotEmpty() &&
                                state.value.phone.isNotEmpty() &&
                                state.value.email.isNotEmpty() &&
                                state.value.password.isNotEmpty()
                            ) {
                                signUpUseCase.invoke(state.value.email, state.value.password)
                                delay(3000)
                                addProfileUseCase.invoke(
                                    companyName = state.value.companyName,
                                    phone = state.value.phone,
                                    email = state.value.email
                                )
                                _state.value = state.value.copy(
                                    isComplete = true
                                )
                            } else {
                                _state.value = state.value.copy(
                                    exception = "Все поля должны быть заполнены!",
                                    progressIndicator = false
                                )
                            }
                        } catch (ex: Exception) {
                            _state.value = state.value.copy(
                                exception = ex.message.toString(),
                                progressIndicator = false
                            )
                            Log.e("supabase", ex.message.toString())
                        }
                    }
                }
            }
            is SignUpEvent.VisualTransformation -> {
                _state.value = state.value.copy(
                    visual = !state.value.visual
                )
            }
            is SignUpEvent.ExceptionClear -> {
                _state.value = state.value.copy(
                    exception = "",
                    emailError = "",
                    passwordError = ""
                )
            }
            is SignUpEvent.ShowProgressIndicator -> {
                _state.value = state.value.copy(
                    progressIndicator = true
                )
            }
        }
    }
}