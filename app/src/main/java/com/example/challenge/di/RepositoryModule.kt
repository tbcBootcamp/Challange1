package com.example.challenge.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.challenge.data.common.HandleResponse
import com.example.challenge.data.repository.connection.ConnectionsRepositoryImpl
import com.example.challenge.data.repository.datastore.DataStoreRepositoryImpl
import com.example.challenge.data.repository.log_in.LogInRepositoryImpl
import com.example.challenge.data.service.connection.ConnectionsService
import com.example.challenge.data.service.log_in.LogInService
import com.example.challenge.domain.repository.connection.ConnectionsRepository
import com.example.challenge.domain.repository.datastore.DataStoreRepository
import com.example.challenge.domain.repository.log_in.LogInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLogInRepository(
        logInService: LogInService,
        handleResponse: HandleResponse
    ): LogInRepository {
        return LogInRepositoryImpl(logInService = logInService, handleResponse = handleResponse)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore = dataStore)
    }

    @Singleton
    @Provides
    fun provideConnectionsRepository(
        connectionsService: ConnectionsService,
        handleResponse: HandleResponse
    ): ConnectionsRepository {
        return ConnectionsRepositoryImpl(
            connectionsService = connectionsService,
            handleResponse = handleResponse
        )
    }

}