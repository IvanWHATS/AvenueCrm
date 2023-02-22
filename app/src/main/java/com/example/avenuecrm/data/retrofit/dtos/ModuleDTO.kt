package com.example.avenuecrm.data.retrofit.dtos

import com.example.avenuecrm.data.models.Module
import com.google.gson.annotations.SerializedName

data class ModuleDTO (
    val name: String? = null,
    val link: String? = null,
    val id: Int? = null,
    @SerializedName("childs") val subModules: List<Module>? = null
)