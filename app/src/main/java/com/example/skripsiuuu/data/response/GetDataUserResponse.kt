package com.example.skripsiuuu.data.response

import com.google.gson.annotations.SerializedName

data class GetDataUserResponse (
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("balance")
    val balance: Float

)


