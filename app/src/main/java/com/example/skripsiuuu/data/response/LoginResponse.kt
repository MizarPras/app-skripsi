package com.example.skripsiuuu.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("name")
    val name: String,


    @field:SerializedName("token")
    val token: String

)
