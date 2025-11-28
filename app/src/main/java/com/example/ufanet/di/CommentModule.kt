package com.example.ufanet.di

import com.example.ufanet.feature_app.data.repositoryImplementation.CommentRepositoryImpl
import com.example.ufanet.feature_app.domain.repository.CommentRepository
import com.example.ufanet.feature_app.domain.usecase.GetCommentsForApplicationUseCase
import org.koin.dsl.module

val moduleComment = module {
    single<CommentRepository> {
        CommentRepositoryImpl()
    }
    factory<GetCommentsForApplicationUseCase> {
        GetCommentsForApplicationUseCase(get())
    }
}