package com.example.ufanet.di

import android.content.Context
import com.example.ufanet.feature_app.data.repositoryImplementation.ApplicationRepositoryImpl
import com.example.ufanet.feature_app.data.repositoryImplementation.AuthRepositoryImpl
import com.example.ufanet.feature_app.data.repositoryImplementation.CommentRepositoryImpl
import com.example.ufanet.feature_app.data.repositoryImplementation.CurrentSessionRepositoryImpl
import com.example.ufanet.feature_app.data.repositoryImplementation.ProfileRepositoryImpl
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository
import com.example.ufanet.feature_app.domain.repository.AuthRepository
import com.example.ufanet.feature_app.domain.repository.CommentRepository
import com.example.ufanet.feature_app.domain.repository.CurrentSessionRepository
import com.example.ufanet.feature_app.domain.repository.ProfileRepository
import com.example.ufanet.feature_app.domain.usecase.AddApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.AddCommentUseCase
import com.example.ufanet.feature_app.domain.usecase.AddProfileUseCase
import com.example.ufanet.feature_app.domain.usecase.DeleteUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.GetAllApplicationsUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationForUpdateUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationMapInfoUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationsUseCase
import com.example.ufanet.feature_app.domain.usecase.GetCommentsForApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.GetCurrentUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.GetFilterApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.GetProfileStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.GetProfileUseCase
import com.example.ufanet.feature_app.domain.usecase.LoadUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.LoadUserStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.RemoveApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.SaveUserIdUseCase
import com.example.ufanet.feature_app.domain.usecase.SignInUseCase
import com.example.ufanet.feature_app.domain.usecase.SignOutUseCase
import com.example.ufanet.feature_app.domain.usecase.SignUpUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateApplicationCommentsCountUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateApplicationStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateProfileUseCase
import com.example.ufanet.feature_app.domain.usecase.ValidateCredentialsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideApplicationRepository(loadUserIdUseCase: LoadUserIdUseCase): ApplicationRepository{
        return ApplicationRepositoryImpl(loadUserIdUseCase)
    }

    @Provides
    fun provideAuthRepository(saveUserIdUseCase: SaveUserIdUseCase,
                              deleteUserIdUseCase: DeleteUserIdUseCase,
                              getProfileStatusUseCase: GetProfileStatusUseCase): AuthRepository{
        return AuthRepositoryImpl(saveUserIdUseCase, deleteUserIdUseCase, getProfileStatusUseCase)
    }

    @Provides
    fun provideCommentRepository(loadUserIdUseCase: LoadUserIdUseCase): CommentRepository{
        return CommentRepositoryImpl(loadUserIdUseCase)
    }

    @Provides
    fun provideCurrentSessionRepository(@ApplicationContext context: Context): CurrentSessionRepository{
        return CurrentSessionRepositoryImpl(context)
    }

    @Provides
    fun provideProfileRepository(loadUserIdUseCase: LoadUserIdUseCase): ProfileRepository{
        return ProfileRepositoryImpl(loadUserIdUseCase)
    }

    @Provides
    fun provideAddApplicationUseCase(applicationRepository: ApplicationRepository): AddApplicationUseCase{
        return AddApplicationUseCase(applicationRepository)
    }

    @Provides
    fun provideAddCommentUseCase(commentRepository: CommentRepository): AddCommentUseCase{
        return AddCommentUseCase(commentRepository)
    }

    @Provides
    fun provideAddProfileUseCase(profileRepository: ProfileRepository): AddProfileUseCase{
        return AddProfileUseCase(profileRepository)
    }

    @Provides
    fun provideGetAllApplicationsUseCase(applicationRepository: ApplicationRepository): GetAllApplicationsUseCase{
        return GetAllApplicationsUseCase(applicationRepository)
    }

    @Provides
    fun provideGetApplicationForUpdateUseCase(applicationRepository: ApplicationRepository): GetApplicationForUpdateUseCase{
        return GetApplicationForUpdateUseCase(applicationRepository)
    }

    @Provides
    fun provideGetApplicationStatusUseCase(applicationRepository: ApplicationRepository): GetApplicationStatusUseCase{
        return GetApplicationStatusUseCase(applicationRepository)
    }

    @Provides
    fun provideGetApplicationsUseCase(applicationRepository: ApplicationRepository): GetApplicationsUseCase{
        return GetApplicationsUseCase(applicationRepository)
    }

    @Provides
    fun provideGetCommentsForApplicationUseCase(commentRepository: CommentRepository): GetCommentsForApplicationUseCase{
        return GetCommentsForApplicationUseCase(commentRepository)
    }

    @Provides
    fun provideGetCurrentUserIdUseCase(authRepository: AuthRepository): GetCurrentUserIdUseCase{
        return GetCurrentUserIdUseCase(authRepository)
    }

    @Provides
    fun provideGetFilterApplicationUseCase(applicationRepository: ApplicationRepository): GetFilterApplicationUseCase{
        return GetFilterApplicationUseCase(applicationRepository)
    }

    @Provides
    fun provideGetProfileStatusUseCase(profileRepository: ProfileRepository): GetProfileStatusUseCase{
        return GetProfileStatusUseCase(profileRepository)
    }

    @Provides
    fun provideGetProfileUseCase(profileRepository: ProfileRepository): GetProfileUseCase{
        return GetProfileUseCase(profileRepository)
    }

    @Provides
    fun provideLoadUserIdUseCase(currentSessionRepository: CurrentSessionRepository): LoadUserIdUseCase{
        return LoadUserIdUseCase(currentSessionRepository)
    }

    @Provides
    fun provideRemoveApplicationUseCase(applicationRepository: ApplicationRepository): RemoveApplicationUseCase{
        return RemoveApplicationUseCase(applicationRepository)
    }

    @Provides
    fun provideSaveUserIdUseCase(currentSessionRepository: CurrentSessionRepository): SaveUserIdUseCase{
        return SaveUserIdUseCase(currentSessionRepository)
    }

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase{
        return SignInUseCase(authRepository)
    }

    @Provides
    fun provideSignOutUseCase(authRepository: AuthRepository): SignOutUseCase{
        return SignOutUseCase(authRepository)
    }

    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase{
        return SignUpUseCase(authRepository)
    }

    @Provides
    fun provideUpdateApplicationCommentsCountUseCase(applicationRepository: ApplicationRepository): UpdateApplicationCommentsCountUseCase{
        return UpdateApplicationCommentsCountUseCase(applicationRepository)
    }

    @Provides
    fun provideUpdateApplicationStatusUseCase(applicationRepository: ApplicationRepository): UpdateApplicationStatusUseCase{
        return UpdateApplicationStatusUseCase(applicationRepository)
    }

    @Provides
    fun provideUpdateApplicationUseCase(applicationRepository: ApplicationRepository): UpdateApplicationUseCase{
        return UpdateApplicationUseCase(applicationRepository)
    }

    @Provides
    fun provideUpdateProfileUseCase(profileRepository: ProfileRepository): UpdateProfileUseCase{
        return UpdateProfileUseCase(profileRepository)
    }

    @Provides
    fun provideValidateCredentialsUseCase(): ValidateCredentialsUseCase{
        return ValidateCredentialsUseCase()
    }

    @Provides
    fun provideDeleteUserIdUseCase(currentSessionRepository: CurrentSessionRepository): DeleteUserIdUseCase{
        return DeleteUserIdUseCase(currentSessionRepository)
    }

    @Provides
    fun provideLoadUserStatusUseCase(currentSessionRepository: CurrentSessionRepository): LoadUserStatusUseCase{
        return LoadUserStatusUseCase(currentSessionRepository)
    }

    @Provides
    fun provideGetApplicationMapInfoUseCase(applicationRepository: ApplicationRepository): GetApplicationMapInfoUseCase{
        return GetApplicationMapInfoUseCase(applicationRepository)
    }
}