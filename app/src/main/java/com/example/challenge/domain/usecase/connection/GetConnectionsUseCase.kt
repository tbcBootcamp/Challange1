package com.example.challenge.domain.usecase.connection

import com.example.challenge.domain.repository.connection.ConnectionsRepository
import javax.inject.Inject

class GetConnectionsUseCase @Inject constructor(private val connectionsRepository: ConnectionsRepository) {

    suspend operator fun invoke() = connectionsRepository.getConnections()
}