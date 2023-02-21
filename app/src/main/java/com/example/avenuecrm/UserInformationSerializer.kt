package com.example.avenuecrm

import androidx.datastore.core.Serializer
import com.example.avenuecrm.data.models.UserInformation
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserInformationSerializer @Inject constructor(
    private val cryptoManager: CryptoManager
): Serializer<UserInformation> {

    override val defaultValue: UserInformation
        get() = UserInformation()

    override suspend fun readFrom(input: InputStream): UserInformation {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = UserInformation.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserInformation, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = UserInformation.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}