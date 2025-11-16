package com.example.ufanet.feature_app.domain.repository

import com.example.ufanet.feature_app.domain.models.Application

interface ApplicationRepository {
    suspend fun addApplication(companyName: String,
                               address: String,
                               phone: String,
                               description: String)
    suspend fun getApplications(): List<Application>
    suspend fun removeApplication(id: Int)
    suspend fun getApplicationForUpdate(id: Int): Application
    suspend fun updateApplication(id: Int,
                                  companyName: String,
                                  address: String,
                                  phone: String,
                                  description: String)
    suspend fun getAllApplications(): List<Application>
}