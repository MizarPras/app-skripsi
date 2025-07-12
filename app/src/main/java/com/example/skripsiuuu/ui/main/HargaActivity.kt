package com.example.skripsiuuu.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsiuuu.R
import com.example.skripsiuuu.databinding.ActivityHargaBinding

class HargaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHargaBinding
    private var list = ArrayList<Harga>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHarga.setHasFixedSize(true)

        list.addAll(getListHarga())
        showRecycleList()
    }

    private fun getListHarga(): ArrayList<Harga> {
        val dataName = resources.getStringArray(R.array.barang_bekas)
        val dataHarga = resources.getStringArray(R.array.data_harga)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listHarga = ArrayList<Harga>()
        for (i in dataName.indices) {
            val harga = Harga(dataName[i], dataHarga[i], dataPhoto.getResourceId(i, -1))
            listHarga.add(harga)
        }
        return listHarga
    }

    private fun showRecycleList(){
        binding.rvHarga.layoutManager = LinearLayoutManager(this)
        val listHargaAdapter = ListHargaAdapter(list)
        binding.rvHarga.adapter = listHargaAdapter
    }
}