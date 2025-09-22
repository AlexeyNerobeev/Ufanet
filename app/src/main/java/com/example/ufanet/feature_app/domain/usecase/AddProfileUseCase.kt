package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.ProfileRepository

class AddProfileUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(companyName: String, phone: String, email: String){
        profileRepository.addNewProfile(companyName = companyName, phone = phone, email = email)
    }
}