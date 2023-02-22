package com.example.avenuecrm.data.retrofit

import com.example.avenuecrm.data.models.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AvenueService {

    @POST("/api/rest/v1.0/user/auth")
    suspend fun authorize(@Body credentials: Credentials): AuthAnswer

    @GET("/api/modules/getTreeModule?")
    suspend fun getTreeModule(@Query("uh") key: String): List<Module>

    @GET("/api/user/getInfo?")
    suspend fun getUserInformation(@Query("uh") key: String): UserInformation

}