package com.danchez.stori.utils.extensions

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `valid email format`() {
        val validEmail = "example@example.com"
        assertTrue(validEmail.isEmailFormatValid())
    }

    @Test
    fun `invalid email format`() {
        val invalidEmail = "example@com"
        assertFalse(invalidEmail.isEmailFormatValid())
    }

    @Test
    fun `empty string`() {
        val emptyEmail = ""
        assertFalse(emptyEmail.isEmailFormatValid())
    }

    @Test
    fun `null string`() {
        val nullEmail: String? = null
        assertFalse(nullEmail.isEmailFormatValid())
    }

    @Test
    fun `random string`() {
        val randomString = "This is not an email address"
        assertFalse(randomString.isEmailFormatValid())
    }
}
