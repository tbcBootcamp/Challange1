package com.example.challenge.domain.usecase.datastore

import com.example.challenge.domain.repository.datastore.DataStoreRepository
import com.example.challenge.domain.user_data_key.PreferenceKeys
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke() = dataStoreRepository.readString(key = PreferenceKeys.TOKEN)
}