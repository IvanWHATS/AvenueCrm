package com.example.avenuecrm.data.serializers

import androidx.datastore.core.Serializer
import com.example.avenuecrm.data.encryption.CryptoManager
import com.example.avenuecrm.data.models.Credentials
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class CredentialsCryptoSerializer @Inject constructor(
    private val cryptoManager: CryptoManager
): Serializer<Credentials> {

    override val defaultValue: Credentials
        get() = Credentials()

    override suspend fun readFrom(input: InputStream): Credentials {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = Credentials.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Credentials, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = Credentials.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}