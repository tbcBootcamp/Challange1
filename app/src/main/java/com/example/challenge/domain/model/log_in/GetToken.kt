package com.example.challenge.domain.model.log_in

data class GetToken(
    val needsMfa: Boolean,
    val accessToken: String,
    val refreshToken: String,
)
