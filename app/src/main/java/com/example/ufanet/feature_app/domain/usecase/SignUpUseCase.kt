package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.data.repositoryImplementation.AuthRepositoryImpl
import com.example.ufanet.feature_app.domain.repository.AuthRepository

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String){
        authRepository.signUp(email, password)
    }
}