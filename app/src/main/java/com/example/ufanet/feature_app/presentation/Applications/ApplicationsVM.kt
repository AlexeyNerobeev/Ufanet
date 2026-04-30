package com.example.ufanet.feature_app.presentation.Applications

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.usecase.AddApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationForUpdateUseCase
import com.example.ufanet.feature_app.domain.usecase.GetCompanyInfoUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateApplicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationsVM @Inject constructor(
    private val addApplicationUseCase: AddApplicationUseCase,
    private val getApplicationForUpdateUseCase: GetApplicationForUpdateUseCase,
    private val updateApplicationUseCase: UpdateApplicationUseCase,
    private val getCompanyInfoUseCase: GetCompanyInfoUseCase
): ViewModel() {
    private val _state = mutableStateOf(ApplicationsState())
    val state: State<ApplicationsState> = _state

    private fun checkChanges() {
        val current = state.value
        val hasChanges = if (current.id > 0) {
            // Для редактирования - проверяем изменения относительно начальных значений
            current.companyName != current.initialCompanyName ||
                    current.address != current.initialAddress ||
                    current.phone != current.initialPhone ||
                    current.description != current.initialDescription
        } else {
            // Для создания - проверяем что все поля заполнены
            current.companyName.isNotBlank() &&
                    current.address.isNotBlank() &&
                    current.phone.isNotBlank() &&
                    current.description.isNotBlank()
        }

        _state.value = current.copy(hasChanges = hasChanges)
    }

    fun onEvent(event: ApplicationsEvent){
        when(event){
            is ApplicationsEvent.EnteredCompanyName ->{
                _state.value = state.value.copy(
                    companyName = event.value
                )
                checkChanges()
            }
            is ApplicationsEvent.EnteredPhone ->{
                _state.value = state.value.copy(
                    phone = event.value
                )
                checkChanges()
            }
            is ApplicationsEvent.EnteredAddress ->{
                _state.value = state.value.copy(
                    address = event.value
                )
                checkChanges()
            }
            is ApplicationsEvent.EnteredDescription ->{
                _state.value = state.value.copy(
                    description = event.value
                )
                checkChanges()
            }
            is ApplicationsEvent.SaveApplication ->{
                if (!state.value.hasChanges) {
                    _state.value = state.value.copy(
                        error = "Все поля должны быть заполнены!"
                    )
                    return
                }

                viewModelScope.launch(Dispatchers.IO){
                    try {
                        _state.value = state.value.copy(isLoading = true)
                        addApplicationUseCase.invoke(
                            state.value.companyName,
                            state.value.address,
                            state.value.phone,
                            state.value.description
                        )
                        _state.value = state.value.copy(
                            isComplete = true,
                            isLoading = false
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = "Ошибка при сохранении заявки"
                        )
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
                            description = application.description,
                            initialCompanyName = application.company_name,
                            initialAddress = application.address,
                            initialPhone = application.phone,
                            initialDescription = application.description,
                            hasChanges = false
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
            is ApplicationsEvent.UpdateApplication -> {
                if (!state.value.hasChanges) {
                    _state.value = state.value.copy(
                        error = "Нет изменений для сохранения"
                    )
                    return
                }

                viewModelScope.launch(Dispatchers.IO){
                    try {
                        _state.value = state.value.copy(isLoading = true)
                        updateApplicationUseCase.invoke(
                            state.value.id,
                            state.value.companyName,
                            state.value.address,
                            state.value.phone,
                            state.value.description
                        )
                        _state.value = state.value.copy(
                            isComplete = true,
                            isLoading = false
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = "Ошибка при обновлении заявки"
                        )
                    }
                }
            }
            ApplicationsEvent.GetCompanyInfo -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val companyInfo = getCompanyInfoUseCase.invoke()
                        _state.value = state.value.copy(
                            companyName = companyInfo.company_name,
                            phone = companyInfo.phone,
                            initialCompanyName = companyInfo.company_name,
                            initialPhone = companyInfo.phone
                        )
                        checkChanges()
                    } catch (e: Exception) {
                        Log.e("getCompanyInfo", e.message.toString())
                    }
                }
            }
            is ApplicationsEvent.ShowError -> {
                _state.value = state.value.copy(
                    error = if (state.value.id > 0)
                        "Нет изменений для сохранения"
                    else
                        "Все поля должны быть заполнены!"
                )
            }
        }
    }
}