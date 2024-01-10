package com.example.challenge.presentation.mapper.connection

import com.example.challenge.domain.model.connection.GetConnection
import com.example.challenge.presentation.model.connection.Connection

fun GetConnection.toPresenter() =
    Connection(
        avatar = avatar,
        email = email,
        id = id,
        fullName = fullName
    )