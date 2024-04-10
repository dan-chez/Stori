package com.danchez.stori.di

import com.danchez.stori.domain.mapper.TransactionResponseToDomainModelMapper
import com.danchez.stori.domain.mapper.TransactionResponseToDomainModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MapperBindingsModule {

    @Binds
    internal abstract fun provideTransactionResponseToDomainModelMapper(impl: TransactionResponseToDomainModelMapperImpl): TransactionResponseToDomainModelMapper
}
