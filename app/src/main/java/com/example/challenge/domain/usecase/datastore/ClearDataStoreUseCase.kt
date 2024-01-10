package com.example.challenge.domain.usecase.datastore

import com.example.challenge.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class ClearDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke() {
        dataStoreRepository.clear()
    }
}