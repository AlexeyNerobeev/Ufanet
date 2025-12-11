package com.example.ufanet.feature_app.presentation.EmployeeProfile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetProfileUseCase
import com.example.ufanet.feature_app.domain.usecase.SignOutUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeProfileVM(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val signOutUseCase: SignOutUseCase
): ViewModel() {
    private val _state = mutableStateOf(EmployeeProfileState())
    val state: State<EmployeeProfileState> = _state

    fun onEvent(event: EmployeeProfileEvent){
        when(event){
            is EmployeeProfileEvent.EnteredPhone -> {
                _state.value = state.value.copy(
                    phone = event.value
                )
            }
            is EmployeeProfileEvent.EnteredCompanyName -> {
                _state.value = state.value.copy(
                    companyName = event.value
                )
            }
            is EmployeeProfileEvent.ClearInfo -> {
                _state.value = state.value.copy(
                    info = ""
                )
            }
            is EmployeeProfileEvent.GetProfile ->{
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val profile = getProfileUseCase.invoke()
                        _state.value = state.value.copy(
                            companyName = profile.company_name,
                            phone = profile.phone,
                            email = profile.email
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
            is EmployeeProfileEvent.UpdateProfile -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        updateProfileUseCase.invoke(companyName = state.value.companyName,
                            phone = state.value.phone)
                        _state.value = state.value.copy(
                            info = "Ваш профиль успешно сохранен!"
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
            is EmployeeProfileEvent.SignOut ->{
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        signOutUseCase.invoke()
                        _state.value = state.value.copy(
                            signOut = true
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
            is EmployeeProfileEvent.ShowAlertSignOut -> {
                _state.value = state.value.copy(
                    alertSignOut = !state.value.alertSignOut
                )
            }
        }
    }
}