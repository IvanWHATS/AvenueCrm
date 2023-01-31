package com.example.avenuecrm.retrofit

import com.example.avenuecrm.models.LoginInformation
import com.example.avenuecrm.retrofit.dtos.AuthAnswerDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AvenueService {

    @POST("user/auth")
    suspend fun authorize(@Body loginInformation: LoginInformation): AuthAnswerDTO


}