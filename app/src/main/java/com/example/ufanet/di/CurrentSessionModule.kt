package com.example.ufanet.di

import com.example.ufanet.feature_app.data.repositoryImplementation.CurrentSessionRepositoryImpl
import com.example.ufanet.feature_app.domain.repository.CurrentSessionRepository
import com.example.ufanet.feature_app.domain.usecase.LoadUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.SaveUserIdUseCase
import org.koin.dsl.module

val moduleCurrentSession = module{
    single<CurrentSessionRepository> {
        CurrentSessionRepositoryImpl(get())
    }
    factory<SaveUserIdUseCase> {
        SaveUserIdUseCase(get())
    }
    factory<LoadUserIdUseCase> {
        LoadUserIdUseCase(get())
    }
}