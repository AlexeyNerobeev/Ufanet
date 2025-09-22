package com.example.ufanet.feature_app.domain.repository

import com.example.ufanet.feature_app.domain.models.Profile
import com.example.ufanet.feature_app.presentation.Applications.ApplicationsEvent

interface ProfileRepository {
    suspend fun addNewProfile(companyName: String, phone: String, email: String)
    suspend fun getProfile(): Profile
    suspend fun updateProfile(companyName: String, phone: String)
    suspend fun getProfileStatus(): Profile
}