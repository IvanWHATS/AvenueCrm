package com.example.avenuecrm.data.retrofit

import com.example.avenuecrm.data.models.UserInformation
import com.example.avenuecrm.data.models.Module
import com.example.avenuecrm.data.retrofit.dtos.AuthAnswerDTO
import com.example.avenuecrm.data.retrofit.dtos.ModuleDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AvenueService {

    @POST("rest/v1.0/user/auth")
    suspend fun authorize(@Body userInformation: UserInformation): AuthAnswerDTO

    @GET("modules/getTreeModule?")
    suspend fun getTreeModule(@Query("uh") key: String): List<ModuleDTO>

}