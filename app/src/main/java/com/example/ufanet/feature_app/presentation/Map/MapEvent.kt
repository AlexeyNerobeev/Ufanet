package com.example.ufanet.feature_app.presentation.Map

import com.yandex.mapkit.geometry.Point

sealed class MapEvent {
    data class OnUserLocationReceived(val point: Point) : MapEvent()
}