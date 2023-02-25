package com.madeThisUp.alienNews.newsApi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

enum class ConnectionStatus {
    CONNECTED,
    ERROR,
    DISCONNECT
}

// Just testing...
object ConnectionStatusManager {
    private val _connectionStatus = MutableStateFlow(ConnectionStatus.DISCONNECT)
    val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus

    fun setStatusConnected() = _connectionStatus.update { ConnectionStatus.CONNECTED }

    fun setError() = _connectionStatus.update { ConnectionStatus.ERROR }
}