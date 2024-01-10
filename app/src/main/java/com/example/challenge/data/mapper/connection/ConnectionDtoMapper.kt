package com.example.challenge.data.mapper.connection

import com.example.challenge.data.model.connection.ConnectionDto
import com.example.challenge.domain.model.connection.GetConnection

fun ConnectionDto.toDomain() =
    GetConnection(
        avatar = avatar,
        email = email,
        id = id,
        fullName = firstName.plus(" ").plus(lastName)
    )