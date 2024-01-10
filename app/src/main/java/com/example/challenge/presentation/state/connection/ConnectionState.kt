package com.example.challenge.presentation.state.connection

import com.example.challenge.presentation.model.connection.Connection

data class ConnectionState(
    private val isLoading: Boolean = false,
    val connections: List<Connection>? = null,
    val errorMessage: String? = null
)
