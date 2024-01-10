package com.example.challenge.domain.usecase.log_in

import com.example.challenge.domain.repository.log_in.LogInRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val logInRepository: LogInRepository) {
    suspend operator fun invoke(email: String, password: String) =
        logInRepository.logIn(email = email, password = password)
}