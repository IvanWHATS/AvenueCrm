package com.example.avenuecrm.data.preferences.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.avenuecrm.data.serializers.CredentialsCryptoSerializer
import com.example.avenuecrm.data.models.Credentials
import com.example.avenuecrm.data.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context,
    serializer: CredentialsCryptoSerializer
): DataStoreRepository {

    private val Context.dataStore: DataStore<Credentials> by dataStore(
        fileName = DATASTORE_FILENAME,
        serializer = serializer
    )
    override suspend fun putCredentials(value: Credentials) {
        context.dataStore.updateData { value }

    }

    override suspend fun getCredentials(): Credentials {
        return context.dataStore.data.first()
    }

    override suspend fun clearCredentials() {
        context.dataStore.updateData { Credentials() }
    }

    companion object {
        private const val DATASTORE_FILENAME = "user-information.json"
    }
}