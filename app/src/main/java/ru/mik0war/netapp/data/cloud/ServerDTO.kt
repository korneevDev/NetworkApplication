package ru.mik0war.netapp.data.cloud

import com.google.gson.annotations.SerializedName

data class ServerDTO(
    @SerializedName("object_name")
    private val name: String,
    private val id: Int
)
