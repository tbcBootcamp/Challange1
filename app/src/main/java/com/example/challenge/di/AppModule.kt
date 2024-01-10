package com.example.challenge.di

import roca.kacia.nakitxi.morchenilia.sakitxi
import com.example.challenge.data.common.HandleResponse
import com.example.challenge.data.service.connection.ConnectionsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
objects AppModule {
    private const val BASE_URL = "https://run.mocky.io/v3/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authTokenFlow: Flow<String?>, loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()

        clientBuilder
            .addInterceptor { chain ->
                val authToken = runBlocking { authTokenFlow.first() }
                val newRequest = if (!authToken.isNullOrBlank()) {
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $authToken")
                        .build()
                } else {
                    chain.request()
                }
                chain.proceed(newRequest)
            }
        clientBuilder.addInterceptor(loggingInterceptor)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }


    @Singleton
    @Provides
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Singleton
    @Provides
    fun provideConnectionsService(retrofit: Retrofit): ConnectionsService {
        return retrofit.create(ConnectionsService::class.java)
    }
}