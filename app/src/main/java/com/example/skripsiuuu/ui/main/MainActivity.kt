package com.example.skripsiuuu.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import java.time.LocalTime
import com.example.skripsiuuu.data.pref.User
import com.example.skripsiuuu.data.pref.UserPreference
import com.example.skripsiuuu.databinding.ActivityMainBinding
import com.example.skripsiuuu.ui.ViewModelFactory
import com.example.skripsiuuu.ui.riwayat.RiwayatActivity
import com.example.skripsiuuu.data.pref.Result
import com.example.skripsiuuu.data.response.GetDataUserResponse
import com.example.skripsiuuu.ui.tukar.TukarActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userModel: User
    private lateinit var userPreference: UserPreference
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val mainViewModel: MainViewModel by viewModels {
            factory
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()
        token = userModel.token.toString()

        val name = ""
        val balance = 0.0f
        mainViewModel.getDataUser(token, name, balance).observe(this) { result ->
            handlegetDataUserResult(result)
        }

        val currentTime = LocalTime.now()
        val greeting = when {
            currentTime.isAfter(
                LocalTime.of(
                    4,
                    59)
            ) && currentTime.isBefore(
                LocalTime.of(
                    11,
                    0)
            ) -> "Selamat Pagi,"

            currentTime.isAfter(
                LocalTime.of(
                    10,
                    59)
            ) && currentTime.isBefore(
                LocalTime.of(
                    15,
                    0
                )
            ) -> "Selamat Siang,"

            currentTime.isAfter(
                LocalTime.of(
                    14,
                    59)
            ) && currentTime.isBefore(
                LocalTime.of(
                    18,
                    0
                )
            ) -> "Selamat Sore,"
            else -> "Selamat Malam,"
        }
        binding.welcome.text = greeting
        binding.riwayat.setOnClickListener {
            startRiwayatActivity()
        }
        binding.harga.setOnClickListener {
            startHargaActivity()
        }
        binding.lokasi.setOnClickListener {
            startMapsActivity()
        }
        binding.tukar.setOnClickListener {
            startTukarActivity()
        }
        binding.buttonLogout.setOnClickListener {
            logout()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            mainViewModel.getDataUser(token, name, balance).observe(this) { result ->
                refresh(result)
            }
        }
    }

    private fun refresh(result: Result<GetDataUserResponse>?) {
            when (result) {
                is Result.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is Result.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    val response = result.data
                    val nama = response.name
                    val balance = response.balance
                    binding.nama.text = nama
                    binding.balance.text = String.format("%.2f", balance)
                    Toast.makeText(this, "Data refreshed!", Toast.LENGTH_SHORT).show()
                }

                is Result.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Coba login ulang yaa!!", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }

    }


    private fun handlegetDataUserResult(result: Result<GetDataUserResponse>?) {
        when(result){
            is Result.Loading -> {}
            is Result.Success -> {
                val response = result.data
                val nama = response.name
                val balance = response.balance
                binding.nama.text = nama
//                binding.balance.text = balance.toString()
                binding.balance.text = String.format("%.2f", balance)
            }
            is Result.Error -> {
                Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                logout()
            }
            else -> {}
        }
    }
    private fun logout() {
        userModel.token = ""
        userPreference.setUser(userModel)
        startActivity(Intent(this, WelcomeActivity::class.java))
    }
    private fun startTukarActivity() {
        val intent = Intent (this@MainActivity, TukarActivity::class.java)
        startActivity(intent)
    }
    private fun startMapsActivity() {
        val intent = Intent (this@MainActivity, MapsActivity::class.java)
        startActivity(intent)
    }
    private fun startHargaActivity() {
        val intent = Intent (this@MainActivity, HargaActivity::class.java)
        startActivity(intent)
    }
    private fun startRiwayatActivity() {
        val intent = Intent (this@MainActivity, RiwayatActivity::class.java)
        startActivity(intent)
    }
}