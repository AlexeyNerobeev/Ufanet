package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class UpdateApplicationUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(id: Int,
                                companyName: String,
                                address: String,
                                phone: String,
                                description: String){
        applicationRepository.updateApplication(id = id,
            companyName = companyName,
            address = address,
            phone = phone,
            description = description)
    }
}