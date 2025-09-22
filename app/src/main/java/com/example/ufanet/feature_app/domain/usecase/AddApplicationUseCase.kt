package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class AddApplicationUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(companyName: String,
                                address: String,
                                phone: String,
                                description: String){
        applicationRepository.addApplication(companyName, address, phone, description)
    }
}