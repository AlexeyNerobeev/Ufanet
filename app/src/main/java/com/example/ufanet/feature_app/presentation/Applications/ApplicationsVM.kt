package com.example.ufanet.feature_app.presentation.Applications

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.common.ErrorAlertDialog
import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.usecase.AddApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationForUpdateUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateApplicationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationsVM(
    private val addApplicationUseCase: AddApplicationUseCase,
    private val getApplicationForUpdateUseCase: GetApplicationForUpdateUseCase,
    private val updateApplicationUseCase: UpdateApplicationUseCase
): ViewModel() {
    private val _state = mutableStateOf(ApplicationsState())
    val state: State<ApplicationsState> = _state

    fun onEvent(event: ApplicationsEvent){
        when(event){
            is ApplicationsEvent.EnteredCompanyName ->{
                _state.value = state.value.copy(
                    companyName = event.value
                )
            }
            is ApplicationsEvent.EnteredPhone ->{
                _state.value = state.value.copy(
                    phone = event.value
                )
            }
            is ApplicationsEvent.EnteredAddress ->{
                _state.value = state.value.copy(
                    address = event.value
                )
            }
            is ApplicationsEvent.EnteredDescription ->{
                _state.value = state.value.copy(
                    description = event.value
                )
            }
            is ApplicationsEvent.SaveApplication ->{
                viewModelScope.launch(Dispatchers.IO){
                    try {
                        if(state.value.address != "" &&
                            state.value.phone != "" &&
                            state.value.description != "" &&
                            state.value.companyName != ""){
                            addApplicationUseCase.invoke(state.value.companyName,
                                state.value.address,
                                state.value.phone,
                                state.value.description)
                            _state.value = state.value.copy(
                                isComplete = true
                            )
                        } else{
                             _state.value = state.value.copy(
                                 error = "Все поля должны быть заполнены!"
                             )
                        }
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
            is ApplicationsEvent.ExceptionClear ->{
                _state.value = state.value.copy(
                    error = ""
                )
            }
            is ApplicationsEvent.GetApplicationForUpdate -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val application: Application = getApplicationForUpdateUseCase.invoke(event.value)
                        _state.value = state.value.copy(
                            id = application.id,
                            companyName = application.company_name,
                            address = application.address,
                            phone = application.phone,
                            description = application.description
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
            is ApplicationsEvent.UpdateApplication -> {
                viewModelScope.launch(Dispatchers.IO){
                    try {
                        if(state.value.address != "" &&
                            state.value.phone != "" &&
                            state.value.description != "" &&
                            state.value.companyName != ""){
                            updateApplicationUseCase.invoke(state.value.id,
                                state.value.companyName,
                                state.value.address,
                                state.value.phone,
                                state.value.description)
                            _state.value = state.value.copy(
                                isComplete = true
                            )
                        } else{
                            _state.value = state.value.copy(
                                error = "Все поля должны быть заполнены!"
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