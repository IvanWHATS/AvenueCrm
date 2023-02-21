package com.example.avenuecrm.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserInformation (
    val login: String? = null,
    val pass: String? = null
)
