package com.example.skripsiuuu.ui.riwayat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.skripsiuuu.R
import com.example.skripsiuuu.data.pref.User
import com.example.skripsiuuu.data.pref.UserPreference
import com.example.skripsiuuu.databinding.ActivityRiwayatBinding
import com.example.skripsiuuu.ui.main.WelcomeActivity

class RiwayatActivity : AppCompatActivity() {

    private lateinit var userModel: User
    private lateinit var userPreference: UserPreference
    private var token : String = ""
    private lateinit var binding : ActivityRiwayatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val listRiwayatFragment = RiwayatFragment()
        val fragment =fragmentManager.findFragmentByTag(RiwayatFragment::class.java.simpleName)

        if (fragment !is RiwayatFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + RiwayatFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.riwayat, listRiwayatFragment, RiwayatFragment::class.java.simpleName)
                .commit()
        }

        userPreference = UserPreference(this)
        userModel = userPreference.getUser()
        token = userModel.token.toString()

        setupSwipeToRefresh()
    }

    private fun setupSwipeToRefresh() {
        val swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            val fragmentManager = supportFragmentManager
            val newRiwayatFragment = RiwayatFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.riwayat, newRiwayatFragment, RiwayatFragment::class.java.simpleName)
                .commit()

            swipeRefreshLayout.isRefreshing = false
        }
    }

    companion object {
        const val EXTRA_KEY = "extra_key"
    }
}