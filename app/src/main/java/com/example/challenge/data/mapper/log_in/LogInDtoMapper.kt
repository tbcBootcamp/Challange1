package com.example.challenge.data.mapper.log_in

import com.example.challenge.data.model.log_in.LogInDto
import com.example.challenge.domain.model.log_in.GetToken

fun LogInDto.toDomain() =
    GetToken(
        needsMfa = needsMfa ?: false,
        accessToken = accessToken ?: "",
        refreshToken = refreshToken ?: ""
    )
