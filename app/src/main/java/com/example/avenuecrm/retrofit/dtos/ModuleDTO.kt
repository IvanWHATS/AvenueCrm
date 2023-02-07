package com.example.avenuecrm.retrofit.dtos

import com.example.avenuecrm.models.Module

data class ModuleDTO (
    val name: String? = null,
    val link: String? = null,
    val id: Int? = null,
    val childs: List<Module>? = null
)