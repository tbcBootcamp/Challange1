package com.example.challenge.di

import com.example.challenge.domain.repository.connection.ConnectionsRepository
import com.example.challenge.domain.repository.datastore.DataStoreRepository
import com.example.challenge.domain.repository.log_in.LogInRepository
import com.example.challenge.domain.usecase.connection.GetConnectionsUseCase
import com.example.challenge.domain.usecase.datastore.ClearDataStoreUseCase
import com.example.challenge.domain.usecase.datastore.GetTokenUseCase
import com.example.challenge.domain.usecase.datastore.SaveTokenUseCase
import com.example.challenge.domain.usecase.log_in.LogInUseCase
import com.example.challenge.domain.usecase.validator.EmailValidatorUseCase
import com.example.challenge.domain.usecase.validator.PasswordValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Singleton
    @Provides
    fun provideEmailValidatorUseCase(
    ): EmailValidatorUseCase {
        return EmailValidatorUseCase()
    }

    @Singleton
    @Provides
    fun providePasswordValidatorUseCase(
    ): PasswordValidatorUseCase {
        return PasswordValidatorUseCase()
    }

    @Singleton
    @Provides
    fun provideSaveTokenUseCase(
        dataStoreRepository: DataStoreRepository
    ): SaveTokenUseCase {
        return SaveTokenUseCase(dataStoreRepository = dataStoreRepository)
    }

    @Singleton
    @Provides
    fun provideGetConnectionsUseCase(
        connectionsRepository: ConnectionsRepository
    ): GetConnectionsUseCase {
        return GetConnectionsUseCase(connectionsRepository = connectionsRepository)
    }

    @Singleton
    @Provides
    fun provideClearDataStoreUseCase(
        dataStoreRepository: DataStoreRepository
    ): ClearDataStoreUseCase {
        return ClearDataStoreUseCase(dataStoreRepository = dataStoreRepository)
    }

    @Singleton
    @Provides
    fun provideGetTokenUseCase(
        dataStoreRepository: DataStoreRepository
    ): GetTokenUseCase {
        return GetTokenUseCase(dataStoreRepository = dataStoreRepository)
    }
}