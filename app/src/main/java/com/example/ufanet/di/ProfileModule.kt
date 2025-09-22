package com.example.ufanet.di

import com.example.ufanet.feature_app.data.repositoryImplementation.ProfileRepositoryImpl
import com.example.ufanet.feature_app.domain.repository.ProfileRepository
import com.example.ufanet.feature_app.domain.usecase.AddProfileUseCase
import com.example.ufanet.feature_app.domain.usecase.GetProfileStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.GetProfileUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateProfileUseCase
import com.example.ufanet.feature_app.presentation.Profile.ProfileEvent
import org.koin.dsl.module

val moduleProfile = module {
    single<ProfileRepository>{
        ProfileRepositoryImpl()
    }
    factory<AddProfileUseCase> {
        AddProfileUseCase(get())
    }
    factory<GetProfileUseCase>{
        GetProfileUseCase(get())
    }
    factory<UpdateProfileUseCase> {
        UpdateProfileUseCase(get())
    }
    factory<GetProfileStatusUseCase> {
        GetProfileStatusUseCase(get())
    }
}