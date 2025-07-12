package com.example.skripsiuuu.ui.riwayat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skripsiuuu.data.UserRepository
import com.example.skripsiuuu.data.response.DataRiwayat

class RiwayatViewModel(private val userRepository: UserRepository): ViewModel() {
    val isLoading: LiveData<Boolean> = userRepository.isLoading

    fun getRiwayat(token: String) : LiveData<PagingData<DataRiwayat>> =
        userRepository.getRiwayat(token).cachedIn(viewModelScope)

}