package com.example.avenuecrm.data.retrofit

import com.example.avenuecrm.data.models.AuthAnswer
import com.example.avenuecrm.data.models.Module
import com.example.avenuecrm.data.models.UserInformation

interface AvenueRepository {

    suspend fun authorize(userInformation: UserInformation): AuthAnswer

    suspend fun getTreeModule(): List<Module>?
}