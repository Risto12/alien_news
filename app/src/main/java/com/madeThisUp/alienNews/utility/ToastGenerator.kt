package com.madeThisUp.alienNews.utility

import android.app.Application
import android.content.Context
import android.widget.Toast

fun Context.showLongToastText(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()