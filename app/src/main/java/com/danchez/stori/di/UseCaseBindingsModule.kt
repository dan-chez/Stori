package com.danchez.stori.di

import com.danchez.stori.domain.usecases.CreateTransactionUseCase
import com.danchez.stori.domain.usecases.CreateTransactionUseCaseImpl
import com.danchez.stori.domain.usecases.GetAccountUseCase
import com.danchez.stori.domain.usecases.GetAccountUseCaseImpl
import com.danchez.stori.domain.usecases.GetTransactionsUseCase
import com.danchez.stori.domain.usecases.GetTransactionsUseCaseImpl
import com.danchez.stori.domain.usecases.LoginUseCase
import com.danchez.stori.domain.usecases.LoginUseCaseImpl
import com.danchez.stori.domain.usecases.SignUpUseCase
import com.danchez.stori.domain.usecases.SignUpUseCaseImpl
import com.danchez.stori.domain.usecases.UpdateBalanceUseCase
import com.danchez.stori.domain.usecases.UpdateBalanceUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class UseCaseBindingsModule {

    @Binds
    internal abstract fun provideSignUpUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    internal abstract fun provideLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    @Binds
    internal abstract fun provideCreateTransactionUseCase(impl: CreateTransactionUseCaseImpl): CreateTransactionUseCase

    @Binds
    internal abstract fun provideGetTransactionUseCase(impl: GetTransactionsUseCaseImpl): GetTransactionsUseCase

    @Binds
    internal abstract fun provideGetAccountUseCase(impl: GetAccountUseCaseImpl): GetAccountUseCase

    @Binds
    internal abstract fun provideUpdateBalanceUseCase(impl: UpdateBalanceUseCaseImpl): UpdateBalanceUseCase
}
