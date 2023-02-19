package com.madeThisUp.alienNews

import android.app.Application
import com.madeThisUp.alienNews.repository.CredentialsPreferencesRepository

class AlienNewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CredentialsPreferencesRepository.initialize(this)
    }
}