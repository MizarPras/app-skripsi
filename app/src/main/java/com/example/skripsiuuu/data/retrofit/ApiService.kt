package com.example.skripsiuuu.data.retrofit

import com.example.skripsiuuu.data.response.GetDataUserResponse
import com.example.skripsiuuu.data.response.GetRiwayatResponse
import com.example.skripsiuuu.data.response.LoginResponse
import com.example.skripsiuuu.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    @POST("users/login")
    suspend fun postLogin(
        @Body body: Map<String, String>
    ): LoginResponse

    @POST("users")
    suspend fun postRegister(
        @Body body: Map<String, String>
    ): RegisterResponse

    @GET("users/current")
    suspend fun getDataUser(
        @Header("Authorization") token: String,
        @Query("name") name: String,
        @Query("balance") balance: Float
    ): GetDataUserResponse

    @GET("riwayat")
    suspend fun getRiwayat(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 1,
    ): GetRiwayatResponse

}