package com.example.avenuecrm.data.preferences.abstraction

import com.example.avenuecrm.data.models.Credentials

interface DataStoreRepository {

    suspend fun putCredentials(value: Credentials)

    suspend fun getCredentials(): Credentials

    suspend fun clearCredentials()
}