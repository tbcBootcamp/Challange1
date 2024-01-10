package com.example.challenge.domain.repository.connection

import com.example.challenge.data.common.Resource
import com.example.challenge.domain.model.connection.GetConnection
import kotlinx.coroutines.flow.Flow

interface ConnectionsRepository {
    suspend fun getConnections(): Flow<Resource<List<GetConnection>>>

}