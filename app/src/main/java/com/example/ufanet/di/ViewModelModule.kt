package com.example.ufanet.di

import com.example.ufanet.feature_app.presentation.Applications.ApplicationsVM
import com.example.ufanet.feature_app.presentation.Home.HomeVM
import com.example.ufanet.feature_app.presentation.Profile.ProfileVM
import com.example.ufanet.feature_app.presentation.SignIn.SignInVM
import com.example.ufanet.feature_app.presentation.SignUp.SignUpVM
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleVM = module {
    viewModel<SignInVM>{
        SignInVM(get(), get())
    }
    viewModel<SignUpVM>{
        SignUpVM(get(), get())
    }
    viewModel<ApplicationsVM>{
        ApplicationsVM(get(), get(), get())
    }
    viewModel<HomeVM> {
        HomeVM(get(), get())
    }
    viewModel<ProfileVM> {
        ProfileVM(get(), get())
    }
}