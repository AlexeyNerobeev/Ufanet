package com.example.ufanet

import com.example.ufanet.feature_app.presentation.EmployeeHome.EmployeeHomeEvent
import org.junit.Assert.*
import org.junit.Test

class EmployeeHomeEventTest {

    @Test
    fun testGetAllApplicationsEvent() {
        val event = EmployeeHomeEvent.GetAllApplications

        assertNotNull(event)
        assertTrue(event is EmployeeHomeEvent)
    }

    @Test
    fun testEventEquality() {
        val event1 = EmployeeHomeEvent.GetAllApplications
        val event2 = EmployeeHomeEvent.GetAllApplications

        assertEquals(event1, event2)
    }

    @Test
    fun testEventToString() {
        val event = EmployeeHomeEvent.GetAllApplications

        val toStringResult = event.toString()

        assertNotNull(toStringResult)
        assertTrue(toStringResult.isNotEmpty())
    }
}