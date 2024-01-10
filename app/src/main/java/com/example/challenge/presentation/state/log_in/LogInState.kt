package com.example.challenge.presentation.state.log_in

data class LogInState(
    val isLoading: Boolean = false,
    val accessToken: String? = null,
    val errorMessage: String? = null
)