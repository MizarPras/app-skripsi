package com.example.skripsiuuu.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.skripsiuuu.R
import com.example.skripsiuuu.databinding.ActivityWelcomeBinding
import com.example.skripsiuuu.ui.loginsingup.LoginActivity
import com.example.skripsiuuu.ui.loginsingup.SignUpActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            startLoginActivity()
        }

        binding.signupButton.setOnClickListener {
            startSignUpActivity()
        }
    }

    private fun startSignUpActivity() {
        startActivity(Intent (this@WelcomeActivity, SignUpActivity::class.java))
    }

    private fun startLoginActivity() {
        startActivity(Intent (this@WelcomeActivity, LoginActivity::class.java))
    }
}