package com.danchez.stori.domain

import com.danchez.stori.data.AuthRepository
import com.danchez.stori.domain.usecases.LoginUseCaseImpl
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    @MockK
    private lateinit var repository: AuthRepository

    @InjectMockKs
    private lateinit var useCase: LoginUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `login success`() = runBlocking {
        val email = "test@example.com"
        val password = "password"

        val mockUser = mockk<FirebaseUser>()
        coEvery { repository.login(email, password) } returns Result.success(mockUser)

        val result = useCase(email, password)

        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(Unit, result.getOrNull())
    }

    @Test
    fun `login failure`() = runBlocking {
        val email = "test@example.com"
        val password = "password"

        coEvery { repository.login(email, password) } returns Result.failure(Exception())

        val result = useCase(email, password)

        Assert.assertTrue(result.isFailure)
    }
}
