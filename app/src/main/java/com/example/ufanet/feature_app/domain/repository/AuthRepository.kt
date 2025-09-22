package com.example.ufanet.feature_app.domain.repository

interface AuthRepository {
    suspend fun signIn(inputEmail: String, inputPassword: String)
    suspend fun signUp(inputEmail: String, inputPassword: String)
}