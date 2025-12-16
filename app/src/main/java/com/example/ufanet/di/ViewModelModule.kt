package com.example.ufanet.di

import com.example.ufanet.feature_app.presentation.Applications.ApplicationsVM
import com.example.ufanet.feature_app.presentation.Comments.CommentsVM
import com.example.ufanet.feature_app.presentation.EmployeeHome.EmployeeHomeVM
import com.example.ufanet.feature_app.presentation.EmployeeProfile.EmployeeProfileEvent
import com.example.ufanet.feature_app.presentation.EmployeeProfile.EmployeeProfileVM
import com.example.ufanet.feature_app.presentation.EmployeeSearch.EmployeeSearchVM
import com.example.ufanet.feature_app.presentation.Home.HomeVM
import com.example.ufanet.feature_app.presentation.Profile.ProfileVM
import com.example.ufanet.feature_app.presentation.SignIn.SignInVM
import com.example.ufanet.feature_app.presentation.SignUp.SignUpVM
import com.example.ufanet.feature_app.presentation.Splash.SplashScreenVM
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleVM = module {
    viewModel<SignInVM>{
        SignInVM(get(), get(), get())
    }
    viewModel<SignUpVM>{
        SignUpVM(get(), get(), get())
    }
    viewModel<ApplicationsVM>{
        ApplicationsVM(get(), get(), get())
    }
    viewModel<HomeVM> {
        HomeVM(get(), get())
    }
    viewModel<ProfileVM> {
        ProfileVM(get(), get(), get())
    }
    viewModel<EmployeeHomeVM> {
        EmployeeHomeVM(get())
    }
    viewModel<EmployeeProfileVM> {
        EmployeeProfileVM(get(), get(), get())
    }
    viewModel<CommentsVM> {
        CommentsVM(get(), get(), get(), get(), get())
    }
    viewModel<EmployeeSearchVM> {
        EmployeeSearchVM(get())
    }
    viewModel<SplashScreenVM> {
        SplashScreenVM(get(), get())
    }
}