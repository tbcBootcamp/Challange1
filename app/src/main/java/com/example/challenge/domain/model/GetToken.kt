package com.example.challenge.domain.model

data class GetToken(
    val needsMfa: Boolean,
    val accessToken: String,
    val refreshToken: String,
)
