package com.example.skripsiuuu.ui.loginsingup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.skripsiuuu.R
import com.example.skripsiuuu.data.pref.Result
import com.example.skripsiuuu.data.response.RegisterResponse
import com.example.skripsiuuu.databinding.ActivitySignUpBinding
import com.example.skripsiuuu.ui.ViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val signUpViewModel: SignUpViewModel by viewModels {
            factory
        }

        binding.SignUpButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showMessage("Nama, email, dan password tidak boleh kosong.")
                return@setOnClickListener
            }

            signUpViewModel.postRegister(name, email, password).observe(this) { result ->
                handleSignUpResult(result)
            }
        }
    }

    private fun handleSignUpResult(result: Result<RegisterResponse>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is Result.Success -> {
                binding.progressBar.visibility = View.GONE
                showMessageAndNavigate("Register Berhasil")
            }
            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
                showMessage("Sign Up ${result.error}")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showMessageAndNavigate(message: String) {
        showMessage(message)
        navigateToLogin()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
    }

}