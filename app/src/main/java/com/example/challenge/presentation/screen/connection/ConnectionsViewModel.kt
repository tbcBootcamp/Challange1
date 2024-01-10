package com.example.challenge.presentation.screen.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.data.common.Resource
import com.example.challenge.domain.usecase.connection.GetConnectionsUseCase
import com.example.challenge.domain.usecase.datastore.ClearDataStoreUseCase
import com.example.challenge.presentation.event.conection.ConnectionEvent
import com.example.challenge.presentation.event.log_in.LogInEvent
import com.example.challenge.presentation.mapper.connection.toPresenter
import com.example.challenge.presentation.screen.log_in.LogInViewModel
import com.example.challenge.presentation.state.connection.ConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConnectionsViewModel @Inject constructor(
    private val getConnectionsUseCase: GetConnectionsUseCase,
    private val clearDataStoreUseCase: ClearDataStoreUseCase
) :
    ViewModel() {
    private val _connectionState = MutableStateFlow(ConnectionState())
    val connectionState: SharedFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ConnectionUiEvent>()
    val uiEvent: SharedFlow<ConnectionUiEvent> get() = _uiEvent

    fun onEvent(event: ConnectionEvent) {
        when (event) {
            is ConnectionEvent.FetchConnections -> fetchConnections()
            is ConnectionEvent.LogOut -> logOut()
            is ConnectionEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun fetchConnections() {
        viewModelScope.launch {
            getConnectionsUseCase().collect {
                when (it) {
                    is Resource.Loading -> _connectionState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Success -> {
                        _connectionState.update { currentState -> currentState.copy(connections = it.data.map { it.toPresenter() }) }
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun logOut(){
        viewModelScope.launch {
            clearDataStoreUseCase()
            _uiEvent.emit(ConnectionUiEvent.NavigateToLogIn)
        }
    }

    private fun updateErrorMessage(message: String?) {
        _connectionState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface ConnectionUiEvent {
        object NavigateToLogIn : ConnectionUiEvent
    }
}