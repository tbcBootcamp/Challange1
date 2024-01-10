package com.example.challenge.domain.repository

import com.example.challenge.data.common.Resource
import com.example.challenge.domain.model.GetToken
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun logIn(email: String, password: String): Flow<Resource<GetToken>>
}