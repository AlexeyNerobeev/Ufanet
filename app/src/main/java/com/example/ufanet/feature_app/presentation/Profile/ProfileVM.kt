package com.example.ufanet.feature_app.presentation.Profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.models.Profile
import com.example.ufanet.feature_app.domain.usecase.GetProfileUseCase
import com.example.ufanet.feature_app.domain.usecase.SignOutUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileVM(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val signOutUseCase: SignOutUseCase
): ViewModel() {
    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    fun onEvent(event: ProfileEvent){
        when(event){
            is ProfileEvent.EnteredPhone -> {
                _state.value = state.value.copy(
                    phone = event.value
                )
            }
            is ProfileEvent.EnteredCompanyName -> {
                _state.value = state.value.copy(
                    companyName = event.value
                )
            }
            is ProfileEvent.ClearInfo -> {
                _state.value = state.value.copy(
                    info = ""
                )
            }
            is ProfileEvent.GetProfile ->{
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
            is ProfileEvent.UpdateProfile -> {
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
            is ProfileEvent.SignOut -> {
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
            is ProfileEvent.ShowAlertSignOut -> {
                _state.value = state.value.copy(
                    alertSignOut = !state.value.alertSignOut
                )
            }
        }
    }
}