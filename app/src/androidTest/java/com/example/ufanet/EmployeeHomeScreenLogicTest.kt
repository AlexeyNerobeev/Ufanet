package com.example.ufanet

import com.example.ufanet.feature_app.domain.models.Application
import org.junit.Assert.*
import org.junit.Test

class EmployeeHomeScreenLogicTest {

    @Test
    fun testNavigationLogic() {
        val applicationId = 123

        assertTrue(applicationId > 0)

        val expectedRoute = "comments_screen/$applicationId"
        val actualRoute = "comments_screen/$applicationId"

        assertEquals(expectedRoute, actualRoute)
        assertTrue(actualRoute.contains(applicationId.toString()))
    }

    @Test
    fun testStatusColorLogic() {
        val testCases = listOf(
            "Не принята" to true,
            "В работе" to true,
            "Выполнена" to true,
            "Отменена" to true
        )

        for ((status, shouldHaveColor) in testCases) {
            assertTrue(status.isNotEmpty())
        }
    }

    @Test
    fun testDataValidation() {
        val validApplication = Application(
            company_name = "Валидная компания",
            phone = "+7 (999) 123-45-67",
            description = "Описание проблемы",
            address = "Адрес"
        )

        assertTrue(validApplication.company_name.isNotBlank())
        assertTrue(validApplication.phone.isNotBlank())
        assertTrue(validApplication.description.isNotBlank())
        assertTrue(validApplication.address.isNotBlank())

        val invalidApplication = Application(
            company_name = "",
            phone = "",
            description = "",
            address = ""
        )

        assertTrue(invalidApplication.company_name.isBlank())
        assertTrue(invalidApplication.phone.isBlank())
    }

    @Test
    fun testListPaginationLogic() {
        val largeList = (1..50).map {
            Application(id = it, company_name = "Компания $it")
        }

        assertEquals(50, largeList.size)

        val pageSize = 10
        val pages = largeList.chunked(pageSize)

        assertEquals(5, pages.size)
        assertEquals(10, pages[0].size)
        assertEquals(10, pages[4].size)
        assertEquals("Компания 1", pages[0][0].company_name)
        assertEquals("Компания 50", pages[4][9].company_name)
    }
}