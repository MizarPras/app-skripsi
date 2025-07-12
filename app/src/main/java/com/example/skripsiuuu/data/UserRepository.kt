package com.example.skripsiuuu.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.skripsiuuu.data.database.RiwayatDatabase
import com.example.skripsiuuu.data.database.RiwayatRemoteMediator
import com.example.skripsiuuu.data.pref.Result
import com.example.skripsiuuu.data.response.DataRiwayat
import com.example.skripsiuuu.data.response.GetDataUserResponse
import com.example.skripsiuuu.data.response.LoginResponse
import com.example.skripsiuuu.data.response.RegisterResponse
import com.example.skripsiuuu.data.retrofit.ApiService


class UserRepository (
    private val riwayatDatabase: RiwayatDatabase,
    private val apiService: ApiService
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postRegister(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData{
        emit(Result.Loading)
        try {
            val requestBody = mapOf(
                "name" to name,
                "email" to email,
                "password" to password
            )
            val response = apiService.postRegister(requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("SignUp", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postLogin(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData{
        emit(Result.Loading)
        try {
            val requestBody = mapOf(
                "email" to email,
                "password" to password
            )
            val response = apiService.postLogin(requestBody)
            emit(Result.Success(response))
        } catch (e: Exception){
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getRiwayat(token: String): LiveData<PagingData<DataRiwayat>>{
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = RiwayatRemoteMediator(riwayatDatabase, apiService, token),
            pagingSourceFactory = {
                riwayatDatabase.riwayatDao().getRiwayat()
            }
        ).liveData
    }

    fun getDataUser(
        token: String,
        name: String,
        balance: Float
    ): LiveData<Result<GetDataUserResponse>> = liveData{
        emit(Result.Loading)
        try{
            val response = apiService.getDataUser(token, name, balance)
            emit(Result.Success(response))
        } catch (e: Exception){
            Log.d("Error", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }
}