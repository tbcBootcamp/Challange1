package com.example.challenge.data.service.connection

import com.example.challenge.data.model.connection.ConnectionDto
import retrofit2.Response
import retrofit2.http.GET

interface ConnectionsService {
    @GET("a767611d-135a-42b6-a29c-23dadcd8f153")
    suspend fun getConnections(
    ): Response<List<ConnectionDto>>
}