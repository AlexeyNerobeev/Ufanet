package com.example.ufanet

import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.presentation.EmployeeHome.EmployeeHomeEvent
import com.example.ufanet.feature_app.presentation.EmployeeHome.EmployeeHomeState
import org.junit.Assert.*
import org.junit.Test

class EmployeeHomeIntegrationTest {

    @Test
    fun testCompleteDataFlow() {
        val application = Application(
            id = 1,
            company_name = "Интеграционная тест",
            phone = "+7 (999) 999-99-99",
            description = "Интеграционное тестирование",
            address = "ул. Тестовая, д. 99",
            status = "В работе",
            comments_count = 2
        )

        assertEquals(1, application.id)
        assertEquals("В работе", application.status)

        val state = EmployeeHomeState(
            application = listOf(application)
        )

        assertEquals(1, state.application.size)
        assertEquals("Интеграционная тест", state.application[0].company_name)

        val event = EmployeeHomeEvent.GetAllApplications
        assertNotNull(event)

        val updatedState = state.copy(
            application = state.application + Application(id = 2, company_name = "Новая")
        )

        assertEquals(2, updatedState.application.size)
        assertEquals("Новая", updatedState.application[1].company_name)
    }

    @Test
    fun testDataTransformation() {
        val rawApplications = listOf(
            Application(id = 1, company_name = "   Компания А   ", phone = "123"),
            Application(id = 2, company_name = "компания Б", phone = "456"),
            Application(id = 3, company_name = "КОМПАНИЯ В", phone = "789")
        )

        val processedApplications = rawApplications.map { app ->
            app.copy(
                company_name = app.company_name.trim().uppercase()
            )
        }

        assertEquals("КОМПАНИЯ А", processedApplications[0].company_name)
        assertEquals("КОМПАНИЯ Б", processedApplications[1].company_name)
        assertEquals("КОМПАНИЯ В", processedApplications[2].company_name)
        assertEquals("123", processedApplications[0].phone)
    }

    @Test
    fun testFilteringLogic() {
        val allApplications = listOf(
            Application(id = 1, company_name = "Альфа", status = "Не принята", comments_count = 0),
            Application(id = 2, company_name = "Бета", status = "В работе", comments_count = 1),
            Application(id = 3, company_name = "Гамма", status = "В работе", comments_count = 3),
            Application(id = 4, company_name = "Дельта", status = "Выполнена", comments_count = 5)
        )

        val inProgress = allApplications.filter { it.status == "В работе" }
        assertEquals(2, inProgress.size)
        assertEquals("Бета", inProgress[0].company_name)
        assertEquals("Гамма", inProgress[1].company_name)

        val manyComments = allApplications.filter { it.comments_count > 2 }
        assertEquals(2, manyComments.size)
        assertEquals("Гамма", manyComments[0].company_name)
        assertEquals("Дельта", manyComments[1].company_name)

        val combined = allApplications.filter {
            it.status == "В работе" && it.comments_count > 2
        }
        assertEquals(1, combined.size)
        assertEquals("Гамма", combined[0].company_name)
    }
}