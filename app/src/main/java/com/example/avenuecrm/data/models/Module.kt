package com.example.avenuecrm.data.models

import com.google.gson.annotations.SerializedName

data class Module (
    val name: String? = null,
    val link: String? = null,
    val id: Int? = null,
    @SerializedName("childs") val subModules: List<Module>? = null
)