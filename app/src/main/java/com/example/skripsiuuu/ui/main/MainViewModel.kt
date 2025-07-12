package com.example.skripsiuuu.ui.main

import androidx.lifecycle.ViewModel
import com.example.skripsiuuu.data.UserRepository

class MainViewModel (private val userRepository: UserRepository): ViewModel() {
    fun getDataUser(token:String, name: String, balance: Float) = userRepository.getDataUser(token, name, balance)
}