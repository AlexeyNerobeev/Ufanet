package com.example.ufanet.feature_app.domain.repository

interface CurrentSessionRepository {
    fun saveUser(id: String, status: String)
    fun loadUserId(): String
    fun loadUserStatus(): String
    fun deleteUserId()
}