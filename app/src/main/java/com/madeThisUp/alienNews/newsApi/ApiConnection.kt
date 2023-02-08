package com.madeThisUp.alienNews.newsApi

enum class ConnectionStatus {
    CONNECTED,
    ERROR,
    DISCONNECT
}

private data class ConnectionSettings(
    val url: String,
    val pin: String
)

object ApiConnection {
    private var _connectionStatus = ConnectionStatus.DISCONNECT
    val connectionStatus: ConnectionStatus
        get() {
            return _connectionStatus
        }

    private var settings: ConnectionSettings? = null

    suspend fun testConnectionAndSave(url: String, pin: String) {
        try {
            _connectionStatus = ConnectionStatus.ERROR
            // TODO connect to backend for testing connection
            // if fails return null and update connection status
        } catch(e: Exception) {

        }
    }

    fun disconnect() { _connectionStatus = ConnectionStatus.DISCONNECT }

    fun getSettings() {

    }
}