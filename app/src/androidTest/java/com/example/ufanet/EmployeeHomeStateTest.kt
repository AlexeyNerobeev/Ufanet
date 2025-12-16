package com.example.ufanet

import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.presentation.EmployeeHome.EmployeeHomeState
import org.junit.Assert.*
import org.junit.Test

class EmployeeHomeStateTest {

    @Test
    fun testEmployeeHomeStateDefault() {
        val state = EmployeeHomeState()

        assertTrue(state.application.isEmpty())
        assertEquals(0, state.application.size)
    }

    @Test
    fun testEmployeeHomeStateWithApplications() {
        val applications = listOf(
            Application(id = 1, company_name = "Компания 1"),
            Application(id = 2, company_name = "Компания 2"),
            Application(id = 3, company_name = "Компания 3")
        )

        val state = EmployeeHomeState(application = applications)

        assertEquals(3, state.application.size)
        assertEquals("Компания 1", state.application[0].company_name)
        assertEquals("Компания 3", state.application[2].company_name)
    }

    @Test
    fun testEmployeeHomeStateCopy() {
        val original = EmployeeHomeState(
            application = listOf(Application(id = 1))
        )
        val newApplication = Application(id = 2, company_name = "Новая компания")

        val updated = original.copy(
            application = original.application + newApplication
        )

        assertEquals(2, updated.application.size)
        assertEquals(1, updated.application[0].id)
        assertEquals(2, updated.application[1].id)
        assertEquals("Новая компания", updated.application[1].company_name)
    }
}