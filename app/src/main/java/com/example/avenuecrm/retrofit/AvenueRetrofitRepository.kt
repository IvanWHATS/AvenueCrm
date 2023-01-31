package com.example.avenuecrm.retrofit

import com.example.avenuecrm.models.AuthAnswer
import com.example.avenuecrm.models.LoginInformation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AvenueRetrofitRepository: AvenueRepository {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val service = retrofit.create(AvenueService::class.java)



    companion object{
        const val BASE_URL = "https://avenue.ru40portal.com/api/rest/v1.0/"
    }

    override suspend fun authorize(login: String, password: String): AuthAnswer {
        return service.authorize(LoginInformation(login, password)).run {
            AuthAnswer(
                error = error,
                error_msg = error_msg,
                key = key
            )
        }
    }
}