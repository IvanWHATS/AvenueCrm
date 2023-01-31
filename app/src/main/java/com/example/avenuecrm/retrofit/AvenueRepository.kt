package com.example.avenuecrm.retrofit

import com.example.avenuecrm.models.AuthAnswer

interface AvenueRepository {

    suspend fun authorize(login: String, password: String): AuthAnswer

}