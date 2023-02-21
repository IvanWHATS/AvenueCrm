package com.example.avenuecrm.data.retrofit

import com.example.avenuecrm.data.models.AuthAnswer
import com.example.avenuecrm.data.models.Module
import com.example.avenuecrm.data.models.UserInformation
import com.example.avenuecrm.data.retrofit.dtos.ModuleDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AvenueRetrofitRepository: AvenueRepository {

    private const val BASE_URL = "https://avenue.ru40portal.com/api/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val service = retrofit.create(AvenueService::class.java)

    private var userKey: String? = null


    override suspend fun authorize(userInformation: UserInformation): AuthAnswer =
        service.authorize(userInformation).run {
            userKey = key
            AuthAnswer(
                error = error,
                error_msg = error_msg,
                key = key
            )
        }

    override suspend fun getTreeModule(): List<Module>? =
        userKey?.let { service.getTreeModule(it)}?.map {
            Module(
                name = it.name,
                link = it.link,
                id = it.id,
                childs = it.childs
            )
        }
}