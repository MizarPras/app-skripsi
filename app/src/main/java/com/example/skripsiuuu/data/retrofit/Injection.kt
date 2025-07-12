package com.example.skripsiuuu.data.retrofit

import android.content.Context
import com.example.skripsiuuu.data.UserRepository
import com.example.skripsiuuu.data.database.RiwayatDatabase

object Injection {
    fun provideRepository(context: Context) : UserRepository {
        val database = RiwayatDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserRepository(database, apiService)
    }
}