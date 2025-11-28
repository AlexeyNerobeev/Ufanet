package com.example.ufanet.di

import com.example.ufanet.feature_app.data.repositoryImplementation.ApplicationRepositoryImpl
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository
import com.example.ufanet.feature_app.domain.usecase.AddApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.GetAllApplicationsUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationForUpdateUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationsUseCase
import com.example.ufanet.feature_app.domain.usecase.RemoveApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateApplicationUseCase
import org.koin.dsl.module

val moduleApplications = module{
    single<ApplicationRepository> {
        ApplicationRepositoryImpl()
    }
    factory<AddApplicationUseCase> {
        AddApplicationUseCase(get())
    }
    factory<GetApplicationsUseCase>{
        GetApplicationsUseCase(get())
    }
    factory<RemoveApplicationUseCase> {
        RemoveApplicationUseCase(get())
    }
    factory<GetApplicationForUpdateUseCase>{
        GetApplicationForUpdateUseCase(get())
    }
    factory<UpdateApplicationUseCase>{
        UpdateApplicationUseCase(get())
    }
    factory<GetAllApplicationsUseCase> {
        GetAllApplicationsUseCase(get())
    }
    factory<GetApplicationStatusUseCase> {
        GetApplicationStatusUseCase(get())
    }
}