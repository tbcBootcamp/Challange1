package com.example.challenge.presentation.screen.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.data.common.Resource
import com.example.challenge.domain.usecase.datastore.SaveTokenUseCase
import com.example.challenge.domain.usecase.log_in.LogInUseCase
import com.example.challenge.domain.usecase.validator.EmailValidatorUseCase
import com.example.challenge.domain.usecase.validator.PasswordValidatorUseCase
import com.example.challenge.presentation.event.log_in.LogInEvent
import com.example.challenge.presentation.state.log_in.LogInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: PasswordValidatorUseCase
) : ViewModel() {
    private val _logInState = MutableStateFlow(LogInState())
    val logInState: SharedFlow<LogInState> = _logInState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LogInUiEvent>()
    val uiEvent: SharedFlow<LogInUiEvent> get() = _uiEvent

    fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> validateForm(email = event.email, password = event.password)
            is LogInEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {
            logInUseCase(email = email, password = password).collect {
                when (it) {
                    is Resource.Loading -> _logInState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Success -> {
                        _logInState.update { currentState -> currentState.copy(accessToken = it.data.accessToken) }
                        saveTokenUseCase(it.data.accessToken)
                        _uiEvent.emit(LogInUiEvent.NavigateToConnections)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun validateForm(email: String, password: String) {
        val isEmailValid = emailValidator(email)
        val isPasswordValid = passwordValidator(password)

        val areFieldsValid =
            listOf(isEmailValid, isPasswordValid)
                .all { it }

        if (!areFieldsValid) {
            updateErrorMessage(message = "Fields are not valid!")
            return
        }

        logIn(email = email, password = password)
    }

    private fun updateErrorMessage(message: String?) {
        _logInState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface LogInUiEvent {
        object NavigateToConnections : LogInUiEvent
    }
}



