package com.example.ufanet.feature_app.domain.repository

interface CurrentSessionRepository {
    fun saveUserId(id: String)
    fun loadUserId(): String
}