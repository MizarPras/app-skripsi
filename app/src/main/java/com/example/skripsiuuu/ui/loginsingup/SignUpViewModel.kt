package com.example.skripsiuuu.ui.loginsingup

import androidx.lifecycle.ViewModel
import com.example.skripsiuuu.data.UserRepository

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun postRegister(name: String, email: String, pass: String) = userRepository.postRegister(name, email, pass)
}