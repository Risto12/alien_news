package com.madeThisUp.alienNews.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object AlienNewsCredentialsPreferencesSerializer : Serializer<AlienNewsCredentialsPreferences> {
    override val defaultValue: AlienNewsCredentialsPreferences = AlienNewsCredentialsPreferences.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): AlienNewsCredentialsPreferences {
        try {
            return AlienNewsCredentialsPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Exception during parsing credentials", exception)
        }
    }

    override suspend fun writeTo(t: AlienNewsCredentialsPreferences, output: OutputStream) = t.writeTo(output)
}
