package com.example.ufanet.feature_app.presentation.Map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapVM @Inject constructor(): ViewModel() {
    private val _state = mutableStateOf(MapState())
    val state: State<MapState> = _state

    fun onEvent(event: MapEvent){
        when(event){
            is MapEvent.OnUserLocationReceived -> {
                _state.value = state.value.copy(
                    userLocation = event.point
                )
            }
        }
    }
}