package com.danchez.stori.ui.signup

import com.danchez.stori.domain.usecases.SignUpUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {

    @MockK
    private lateinit var signUpUseCase: SignUpUseCase

    @InjectMockKs
    private lateinit var signUpViewModel: SignUpViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `SignUp success`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        coEvery { signUpUseCase(any(), any(), any()) } returns Result.success(Unit)

        signUpViewModel.signUp()

        Assert.assertEquals(SignUpUIState.UIState.SignUpSuccess, signUpViewModel.signUpState.value.state)
    }

    @Test
    fun `SignUp failure`() = runTest {
        val mockError = Exception("Sign up failed")

        coEvery { signUpUseCase(any(), any(), any()) } returns Result.failure(mockError)

        signUpViewModel.signUp()

        Assert.assertEquals(SignUpUIState.UIState.SignUpFailure, signUpViewModel.signUpState.value.state)
    }

    @Test
    fun `When updateEmail empty value check showEmailError must be true`() {
        signUpViewModel.updateEmail("")
        Assert.assertTrue(signUpViewModel.signUpState.value.showEmailError)
    }

    @Test
    fun `When updateEmail check showEmailError must be false`() {
        signUpViewModel.updateEmail("daniel@mail.com")
        Assert.assertFalse(signUpViewModel.signUpState.value.showEmailError)
    }
}
