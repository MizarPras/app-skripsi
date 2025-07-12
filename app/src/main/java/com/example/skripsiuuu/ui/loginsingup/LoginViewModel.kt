package com.example.skripsiuuu.ui.loginsingup

import androidx.lifecycle.ViewModel
import com.example.skripsiuuu.data.UserRepository

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {
    fun postLogin(email: String, password: String) = userRepository.postLogin(email, password)
}