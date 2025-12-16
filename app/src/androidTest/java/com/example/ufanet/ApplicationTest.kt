package com.example.ufanet


import com.example.ufanet.feature_app.domain.models.Application
import org.junit.Assert.*
import org.junit.Test

class ApplicationTest {

    @Test
    fun testApplicationDefaultValues() {
        val application = Application()

        assertEquals(0, application.id)
        assertEquals("", application.company_name)
        assertEquals("", application.phone)
        assertEquals("", application.description)
        assertEquals("", application.user_id)
        assertEquals("", application.address)
        assertEquals("", application.status)
        assertEquals(0, application.comments_count)
    }

    @Test
    fun testApplicationWithValues() {
        val expectedId = 1
        val expectedCompanyName = "ООО ТехноСервис"
        val expectedPhone = "+7 (999) 123-45-67"
        val expectedDescription = "Не работает интернет"
        val expectedUserId = "user123"
        val expectedAddress = "ул. Ленина, д. 10, кв. 5"
        val expectedStatus = "В работе"
        val expectedCommentsCount = 3

        val application = Application(
            id = expectedId,
            company_name = expectedCompanyName,
            phone = expectedPhone,
            description = expectedDescription,
            user_id = expectedUserId,
            address = expectedAddress,
            status = expectedStatus,
            comments_count = expectedCommentsCount
        )

        assertEquals(expectedId, application.id)
        assertEquals(expectedCompanyName, application.company_name)
        assertEquals(expectedPhone, application.phone)
        assertEquals(expectedDescription, application.description)
        assertEquals(expectedUserId, application.user_id)
        assertEquals(expectedAddress, application.address)
        assertEquals(expectedStatus, application.status)
        assertEquals(expectedCommentsCount, application.comments_count)
    }

    @Test
    fun testApplicationCopy() {
        val original = Application(
            id = 1,
            company_name = "Компания А",
            status = "Не принята"
        )

        val copied = original.copy(status = "В работе")

        assertEquals(1, copied.id)
        assertEquals("Компания А", copied.company_name)
        assertEquals("В работе", copied.status)
    }

    @Test
    fun testApplicationEquality() {
        val app1 = Application(id = 1, company_name = "Тест")
        val app2 = Application(id = 1, company_name = "Тест")
        val app3 = Application(id = 2, company_name = "Тест")

        assertEquals(app1, app2)
        assertNotEquals(app1, app3)
    }
}