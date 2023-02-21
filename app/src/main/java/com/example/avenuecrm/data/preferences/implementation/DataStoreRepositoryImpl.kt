package com.example.avenuecrm.data.preferences.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.avenuecrm.UserInformationSerializer
import com.example.avenuecrm.data.models.UserInformation
import com.example.avenuecrm.data.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context,
    serializer: UserInformationSerializer
): DataStoreRepository {

    private val Context.dataStore: DataStore<UserInformation> by dataStore(
        fileName = DATASTORE_FILENAME,
        serializer = serializer
    )
    override suspend fun putUserInformation(value: UserInformation) {
        context.dataStore.updateData { value }

    }

    override suspend fun getUserInformation(): UserInformation {
        return context.dataStore.data.first()
    }

    override suspend fun clearUserInformation() {
        context.dataStore.updateData { UserInformation() }
    }

    companion object {
        private const val DATASTORE_FILENAME = "user-information.json"
    }
}