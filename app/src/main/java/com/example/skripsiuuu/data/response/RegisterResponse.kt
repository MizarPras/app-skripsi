package com.example.skripsiuuu.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("data")
    val dataRegister: DataRegister
)

data class DataRegister(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("name")
    val name: String
)
