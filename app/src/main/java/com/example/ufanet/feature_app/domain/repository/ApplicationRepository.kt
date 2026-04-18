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
    suspend fun getApplicationStatus(applicationId: Int): Application
    suspend fun updateApplicationStatus(applicationId: Int, status: String)
    suspend fun updateCommentsCount(applicationId: Int, commentsCount: Int)
    suspend fun getFilterApplication(searchText: String, column: String, status: String, commentsCount: Int): List<Application>
    suspend fun getApplicationMapInfo(applicationId: Int): Application
    suspend fun getAllCacheApplications(): List<Application>
    suspend fun saveCacheApplications(applications: List<Application>)
    suspend fun getFilterCacheApplications(searchText: String,
                                           status: String,
                                           commentsCount: Int,
                                           column: String): List<Application>
}