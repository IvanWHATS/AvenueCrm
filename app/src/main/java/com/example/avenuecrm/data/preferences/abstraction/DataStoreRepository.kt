package com.example.avenuecrm.data.preferences.abstraction

import com.example.avenuecrm.data.models.UserInformation

interface DataStoreRepository {

    suspend fun putUserInformation(value: UserInformation)

    suspend fun getUserInformation(): UserInformation

    suspend fun clearUserInformation()
}