package com.danchez.stori.di

import com.danchez.stori.data.AuthRepository
import com.danchez.stori.data.AuthRepositoryImpl
import com.danchez.stori.data.TransactionsRepository
import com.danchez.stori.data.TransactionsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideTransactionsRepository(impl: TransactionsRepositoryImpl): TransactionsRepository = impl
}
