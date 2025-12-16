package com.example.ufanet

import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository
import com.example.ufanet.feature_app.domain.usecase.GetAllApplicationsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetAllApplicationsUseCaseTest {

    class FakeApplicationRepository : ApplicationRepository {
        private val applications = mutableListOf<Application>()
        private var shouldThrowError = false

        fun setApplications(apps: List<Application>) {
            applications.clear()
            applications.addAll(apps)
        }

        fun setShouldThrowError(value: Boolean) {
            shouldThrowError = value
        }

        override suspend fun addApplication(
            companyName: String,
            address: String,
            phone: String,
            description: String
        ) {
            if (shouldThrowError) throw RuntimeException("Test error")
            applications.add(
                Application(
                    id = applications.size + 1,
                    company_name = companyName,
                    address = address,
                    phone = phone,
                    description = description
                )
            )
        }

        override suspend fun getApplications(): List<Application> {
            if (shouldThrowError) throw RuntimeException("Test error")
            return applications.toList()
        }

        override suspend fun getAllApplications(): List<Application> {
            if (shouldThrowError) throw RuntimeException("Test error")
            return applications.toList()
        }

        override suspend fun removeApplication(id: Int) {
            if (shouldThrowError) throw RuntimeException("Test error")
            applications.removeIf { it.id == id }
        }

        override suspend fun getApplicationForUpdate(id: Int): Application {
            if (shouldThrowError) throw RuntimeException("Test error")
            return applications.first { it.id == id }
        }

        override suspend fun updateApplication(
            id: Int,
            companyName: String,
            address: String,
            phone: String,
            description: String
        ) {
            if (shouldThrowError) throw RuntimeException("Test error")
            val index = applications.indexOfFirst { it.id == id }
            if (index != -1) {
                applications[index] = applications[index].copy(
                    company_name = companyName,
                    address = address,
                    phone = phone,
                    description = description
                )
            }
        }

        override suspend fun getApplicationStatus(applicationId: Int): Application {
            if (shouldThrowError) throw RuntimeException("Test error")
            return applications.first { it.id == applicationId }
        }

        override suspend fun updateApplicationStatus(applicationId: Int, status: String) {
            if (shouldThrowError) throw RuntimeException("Test error")
            val index = applications.indexOfFirst { it.id == applicationId }
            if (index != -1) {
                applications[index] = applications[index].copy(status = status)
            }
        }

        override suspend fun updateCommentsCount(applicationId: Int, commentsCount: Int) {
            if (shouldThrowError) throw RuntimeException("Test error")
            val index = applications.indexOfFirst { it.id == applicationId }
            if (index != -1) {
                applications[index] = applications[index].copy(comments_count = commentsCount)
            }
        }

        override suspend fun getFilterApplication(
            searchText: String,
            column: String,
            status: String,
            commentsCount: Int
        ): List<Application> {
            if (shouldThrowError) throw RuntimeException("Test error")
            return applications.filter { app ->
                (searchText.isEmpty() || app.company_name.contains(searchText, ignoreCase = true)) &&
                        (status.isEmpty() || app.status == status) &&
                        (commentsCount < 0 || app.comments_count == commentsCount)
            }
        }
    }

    @Test
    fun testGetAllApplicationsSuccess() = runTest {
        val fakeRepository = FakeApplicationRepository()
        val expectedApplications = listOf(
            Application(id = 1, company_name = "ООО Альфа"),
            Application(id = 2, company_name = "ИП Бета"),
            Application(id = 3, company_name = "ЗАО Гамма")
        )
        fakeRepository.setApplications(expectedApplications)
        val useCase = GetAllApplicationsUseCase(fakeRepository)

        val result = useCase.invoke()

        assertEquals(3, result.size)
        assertEquals("ООО Альфа", result[0].company_name)
        assertEquals("ИП Бета", result[1].company_name)
        assertEquals("ЗАО Гамма", result[2].company_name)
    }

    @Test
    fun testGetAllApplicationsEmpty() = runTest {
        val fakeRepository = FakeApplicationRepository()
        fakeRepository.setApplications(emptyList())
        val useCase = GetAllApplicationsUseCase(fakeRepository)

        val result = useCase.invoke()

        assertTrue(result.isEmpty())
        assertEquals(0, result.size)
    }

    @Test(expected = RuntimeException::class)
    fun testGetAllApplicationsWithError() = runTest {
        val fakeRepository = FakeApplicationRepository()
        fakeRepository.setShouldThrowError(true)
        val useCase = GetAllApplicationsUseCase(fakeRepository)

        useCase.invoke()
    }
}