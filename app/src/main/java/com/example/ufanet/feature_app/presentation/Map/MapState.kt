package com.example.ufanet.feature_app.presentation.Map

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline

data class MapState(
    val enterprisePoint: Point? = null,
    val userLocation: Point? = null,
    val applicationId: Int = 0,
    val enterpriseName: String = "",
    val enterprisePhone: String = "",
    val enterpriseAddress: String = "",
    val route: Polyline? = null,
    val isLoading: Boolean = true,
    val isRouteLoading: Boolean = false,
    val distanceText: String = "",
    val timeText: String = ""
)