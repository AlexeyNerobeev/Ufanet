package com.example.ufanet.di

import com.example.ufanet.feature_app.data.repositoryImplementation.AuthRepositoryImpl
import com.example.ufanet.feature_app.domain.repository.AuthRepository
import com.example.ufanet.feature_app.domain.usecase.GetCurrentUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.SignInUseCase
import com.example.ufanet.feature_app.domain.usecase.SignOutUseCase
import com.example.ufanet.feature_app.domain.usecase.SignUpUseCase
import com.example.ufanet.feature_app.domain.usecase.ValidateCredentialsUseCase
import org.koin.dsl.module

val moduleAuth = module {
    single<AuthRepository> {
        AuthRepositoryImpl()
    }
    factory<SignUpUseCase>{
        SignUpUseCase(get())
    }
    factory<SignInUseCase>{
        SignInUseCase(get())
    }
    factory<GetCurrentUserIdUseCase> {
        GetCurrentUserIdUseCase(get())
    }
    factory<SignOutUseCase> {
        SignOutUseCase(get())
    }
    factory<ValidateCredentialsUseCase>{
        ValidateCredentialsUseCase()
    }
}