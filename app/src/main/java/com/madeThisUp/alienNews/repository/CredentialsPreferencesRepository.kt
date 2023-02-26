package com.madeThisUp.alienNews.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.madeThisUp.alienNews.data.AlienNewsCredentialsPreferences
import com.madeThisUp.alienNews.serializer.AlienNewsCredentialsPreferencesSerializer
import kotlinx.coroutines.flow.Flow

private val Context.alienCredentialsStore: DataStore<AlienNewsCredentialsPreferences> by dataStore(
    fileName = "alien_news_credentials",
    serializer = AlienNewsCredentialsPreferencesSerializer
)

class CredentialsPreferencesRepository private constructor(private val dataStore: DataStore<AlienNewsCredentialsPreferences>) {
    val credentialsFlow: Flow<AlienNewsCredentialsPreferences> = dataStore.data

    suspend fun saveCredentials(username: String, password: String) {
        dataStore.updateData {
            it.toBuilder().setUsername(username).setPassword(password).build()
        }
    }

    suspend fun clearCredentials() =
        dataStore.updateData {
            it.toBuilder().clear().build()
        }

    companion object {
        private var INSTANCE: CredentialsPreferencesRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore : DataStore<AlienNewsCredentialsPreferences> = context.alienCredentialsStore
                INSTANCE = CredentialsPreferencesRepository(dataStore)
            }
        }

        fun get(): CredentialsPreferencesRepository {
            return INSTANCE ?: throw IllegalStateException("Preferences repository not initialized")
        }
    }
}
