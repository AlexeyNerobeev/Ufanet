package com.example.ufanet.feature_app.data.repositoryImplementation

import android.util.Log
import com.example.ufanet.feature_app.data.supabase.Connect.supabase
import com.example.ufanet.feature_app.domain.models.User
import com.example.ufanet.feature_app.domain.repository.AuthRepository
import com.example.ufanet.feature_app.domain.usecase.DeleteUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.GetProfileStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.SaveUserIdUseCase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

class AuthRepositoryImpl(
    private val saveUserIdUseCase: SaveUserIdUseCase,
    private val deleteUserIdUseCase: DeleteUserIdUseCase,
    private val getProfileStatusUseCase: GetProfileStatusUseCase
) : AuthRepository {
    override suspend fun signIn(inputEmail: String, inputPassword: String) {
        supabase.auth.signInWith(Email) {
            email = inputEmail
            password = inputPassword
        }
        val id = getCurrentUserId().id
        val status = getProfileStatusUseCase.invoke().status
        id?.let {
            saveUserIdUseCase.invoke(id, status)
        }
    }

    override suspend fun signUp(inputEmail: String, inputPassword: String) {
        supabase.auth.signUpWith(Email) {
            email = inputEmail
            password = inputPassword
        }
        val id = getCurrentUserId().id
        id?.let {
            saveUserIdUseCase.invoke(id, "клиент")
        }
    }

    override suspend fun getCurrentUserId(): User {
        supabase.auth.awaitInitialization()
        val userId = supabase.auth.currentSessionOrNull()?.user?.id
        return User(id = userId)
    }

    override suspend fun signOut() {
        supabase.auth.signOut()
        deleteUserIdUseCase.invoke()
    }
}