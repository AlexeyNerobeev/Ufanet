package com.example.ufanet.feature_app.domain.usecase

import com.example.ufanet.feature_app.domain.models.EmployeeStats
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository

class GetEmployeeStatsUseCase(private val applicationRepository: ApplicationRepository) {
    suspend operator fun invoke(employeeId: String): EmployeeStats{
        return applicationRepository.getEmployeeStats(employeeId)
    }
}