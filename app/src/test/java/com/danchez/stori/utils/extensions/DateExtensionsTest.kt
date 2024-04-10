package com.danchez.stori.utils.extensions

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateExtensionsTest {

    @Test
    fun `valid date format`() {
        val date = Date()
        val expectedDate = SimpleDateFormat(MMM_DD_YYYY, Locale.getDefault()).format(date)
        assertEquals(expectedDate, date.formatDateToString())
    }

    @Test
    fun `custom date format`() {
        val date = Date()
        val customPattern = "dd-MM-yyyy"
        val expectedDate = SimpleDateFormat(customPattern, Locale.getDefault()).format(date)
        assertEquals(expectedDate, date.formatDateToString(customPattern))
    }

    @Test
    fun `empty pattern`() {
        val date = Date()
        assertEquals("", date.formatDateToString(""))
    }
}
