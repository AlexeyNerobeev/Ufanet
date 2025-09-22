package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.ProfileRepository

class UpdateProfileUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(companyName: String, phone: String){
        profileRepository.updateProfile(companyName = companyName, phone = phone)
    }
}