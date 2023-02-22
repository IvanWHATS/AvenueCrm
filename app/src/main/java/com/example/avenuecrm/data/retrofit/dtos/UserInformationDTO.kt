package com.example.avenuecrm.data.retrofit.dtos

import com.example.avenuecrm.data.models.UserFileInformation
import com.google.gson.annotations.SerializedName

data class UserInformationDTO (
    @SerializedName("user_name") var name: String? = null,
    @SerializedName("user_fio_full") var fioFull: String? = null,
    @SerializedName("files") var file: UserFileInformation? = null
)