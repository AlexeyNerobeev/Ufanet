package com.example.ufanet.feature_app.presentation.Home

import com.example.ufanet.feature_app.domain.models.Application

data class HomeState(
    val application: List<Application> = listOf()
)
