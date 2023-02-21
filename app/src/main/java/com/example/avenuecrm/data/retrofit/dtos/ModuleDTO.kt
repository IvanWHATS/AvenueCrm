package com.example.avenuecrm.data.retrofit.dtos

import com.example.avenuecrm.data.models.Module

data class ModuleDTO (
    val name: String? = null,
    val link: String? = null,
    val id: Int? = null,
    val childs: List<Module>? = null
)