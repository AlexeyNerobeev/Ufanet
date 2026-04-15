package com.example.ufanet.feature_app.presentation.Map

import com.yandex.mapkit.geometry.Point

data class MapState(
    val enterprisePoint: Point? = null,
    val userLocation: Point? = null
)