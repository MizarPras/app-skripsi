package com.example.skripsiuuu.data.response

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)
