package com.example.avenuecrm.data.models

import com.google.gson.annotations.SerializedName

data class UserFileInformation (
    @SerializedName("files_id") var id: String? = null,
    @SerializedName("files_name") var name: String? = null,
    @SerializedName("files_path") var path: String? = null
)