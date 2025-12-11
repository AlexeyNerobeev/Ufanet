package com.example.ufanet.feature_app.data.repositoryImplementation

import com.example.ufanet.feature_app.data.supabase.Connect.supabase
import com.example.ufanet.feature_app.domain.models.User
import com.example.ufanet.feature_app.domain.repository.AuthRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

class AuthRepositoryImpl: AuthRepository {
    override suspend fun signIn(inputEmail: String, inputPassword: String) {
        supabase.auth.signInWith(Email){
            email = inputEmail
            password = inputPassword
        }
    }

    override suspend fun signUp(inputEmail: String, inputPassword: String) {
        supabase.auth.signUpWith(Email){
            email = inputEmail
            password = inputPassword
        }
    }

    override suspend fun getCurrentUserId(): User {
        supabase.auth.awaitInitialization()
        val userId = supabase.auth.currentSessionOrNull()?.user?.id
        return User(id = userId)
    }

    override suspend fun signOut() {
        supabase.auth.signOut()
    }
}