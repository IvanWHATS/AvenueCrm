package com.example.avenuecrm.data.models

import com.google.gson.annotations.SerializedName

data class UserInformation(
    @SerializedName("user_name") var name: String? = null,
    @SerializedName("user_fio_full") var fioFull: String? = null,
    @SerializedName("files") var file: UserFileInformation? = null
)
