package com.example.avenuecrm.data.models

import com.google.gson.annotations.SerializedName

data class AuthAnswer (
    val error: Int? = null,
    @SerializedName("error_msg") val errorMessage: String? = null,
    val key: String? = null
)