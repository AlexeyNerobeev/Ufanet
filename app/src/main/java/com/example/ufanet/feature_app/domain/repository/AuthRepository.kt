package com.example.ufanet.feature_app.domain.repository

import com.example.ufanet.feature_app.domain.models.User

interface AuthRepository {
    suspend fun signIn(inputEmail: String, inputPassword: String)
    suspend fun signUp(inputEmail: String, inputPassword: String)
    suspend fun getCurrentUserId(): User
    suspend fun signOut()
}