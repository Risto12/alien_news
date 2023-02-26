package com.madeThisUp.alienNews.utility

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

// Temporal
object PhonePermissionHandler {
    fun hasReadContactsPermission(context: Context) = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED


}