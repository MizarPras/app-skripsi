package com.example.skripsiuuu.ui.loginsingup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.skripsiuuu.R
import com.example.skripsiuuu.data.pref.Result
import com.example.skripsiuuu.data.pref.User
import com.example.skripsiuuu.data.pref.UserPreference
import com.example.skripsiuuu.data.response.LoginResponse
import com.example.skripsiuuu.databinding.ActivityLoginBinding
import com.example.skripsiuuu.ui.ViewModelFactory
import com.example.skripsiuuu.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private var userModel: User = User()
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val loginViewModel: LoginViewModel by viewModels {
            factory
        }
        userPreference = UserPreference(this)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password =binding.passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showMessage("Email dan password tidak boleh kosong.")
                return@setOnClickListener
            }

            loginViewModel.postLogin(email, password).observe(this) { result ->
                handleLoginResult(result)
            }
        }

    }

    private fun handleLoginResult(result: Result<LoginResponse>?) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is Result.Success -> {
                binding.progressBar.visibility = View.GONE
                val response = result.data
//                val message = response.message
                val token = response.token

                showMessageAndNavigate("Login Berhasil", token)
            }
            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
                showMessage("Login ${result.error}")
            }

            else -> {}
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showMessageAndNavigate(message: String, token: String) {
        showMessage(message)
        saveToken(token)
        navigateToMain(token)
    }
    private fun navigateToMain(token: String) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra(EXTRA_KEY, token)
        startActivity(intent)
    }

    private fun saveToken(token: String) {
        userModel.token = token
        userPreference.setUser(userModel)
    }

    companion object {
        const val EXTRA_KEY = "extra_key"
    }
}



