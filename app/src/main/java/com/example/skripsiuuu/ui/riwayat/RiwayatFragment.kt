package com.example.skripsiuuu.ui.riwayat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsiuuu.R
import com.example.skripsiuuu.data.pref.UserPreference
import com.example.skripsiuuu.data.response.DataRiwayat
import com.example.skripsiuuu.databinding.FragmentRiwayatBinding
import com.example.skripsiuuu.ui.ViewModelFactory

class RiwayatFragment: Fragment(R.layout.fragment_riwayat) {
    private lateinit var factory: ViewModelFactory
    private val riwayatViewModel: RiwayatViewModel by viewModels {
        factory
    }

    private lateinit var binding : FragmentRiwayatBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RiwayatAdapter
    private var token: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRiwayatBinding.bind(view)
        factory =ViewModelFactory.getInstance(requireActivity())
        setupRecyclerView()

        val userPreference = UserPreference(requireActivity())
        val userModel = userPreference.getUser()
        token = userModel.token.toString()

        getRiwayatList()
    }

    private fun getRiwayatList() {
        val authToken = token
        if (token.isEmpty()) {
            Log.e("RiwayatFragment", "Token is empty")
        } else {
            Log.d("RiwayatFragment", "Using token: $token")
        }
        riwayatViewModel.getRiwayat(authToken).observe(viewLifecycleOwner) { pagingData ->
            if (pagingData != null) {
                Log.d("RiwayatFragment", "Paging data received: ${pagingData}")
                adapter.submitData(lifecycle, pagingData)
            } else {
                Log.e("RiwayatFragment", "Paging data is null")
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recycleviewRiwayat
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RiwayatAdapter()

        recyclerView.adapter = adapter

    }

}