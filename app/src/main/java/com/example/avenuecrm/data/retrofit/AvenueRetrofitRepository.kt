package com.example.avenuecrm.data.retrofit

import com.example.avenuecrm.data.models.AuthAnswer
import com.example.avenuecrm.data.models.Module
import com.example.avenuecrm.data.models.Credentials
import com.example.avenuecrm.data.models.UserInformation
import com.example.avenuecrm.data.retrofit.dtos.ModuleDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AvenueRetrofitRepository: AvenueRepository {

    private const val BASE_URL = "https://avenue.ru40portal.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val service = retrofit.create(AvenueService::class.java)

    private var userKey: String? = null


    override suspend fun authorize(credentials: Credentials): AuthAnswer =
        service.authorize(credentials).apply {
            userKey = key
        }

    override suspend fun getTreeModule(): List<Module>? =
        userKey?.let { service.getTreeModule(it)}


    override suspend fun getUserInformation(): UserInformation? =
        userKey?.let { service.getUserInformation(it) }?.let { ui ->
            ui.file?.let {
                ui.copy(file = it.copy(path = BASE_URL + it.path))
            }
        }
}