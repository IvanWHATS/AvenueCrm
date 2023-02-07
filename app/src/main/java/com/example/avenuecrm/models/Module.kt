package com.example.avenuecrm.models

data class Module (
    val name: String? = null,
    val link: String? = null,
    val id: Int? = null,
    val childs: List<Module>? = null
)