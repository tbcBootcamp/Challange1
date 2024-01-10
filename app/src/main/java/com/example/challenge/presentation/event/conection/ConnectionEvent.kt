package com.example.challenge.presentation.event.conection

sealed class ConnectionEvent {
    object FetchConnections : ConnectionEvent()
    object LogOut : ConnectionEvent()
    object ResetErrorMessage : ConnectionEvent()

}