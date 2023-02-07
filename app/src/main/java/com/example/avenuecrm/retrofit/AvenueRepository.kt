package com.example.avenuecrm.retrofit

import com.example.avenuecrm.models.AuthAnswer
import com.example.avenuecrm.models.Module

interface AvenueRepository {



    suspend fun authorize(login: String, password: String): AuthAnswer

    suspend fun getTreeModule(): List<Module>?
}