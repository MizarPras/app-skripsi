package com.example.skripsiuuu.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsiuuu.databinding.RowHargaBinding

class ListHargaAdapter(private val listHarga : ArrayList<Harga>) : RecyclerView.Adapter<ListHargaAdapter.ListViewHolder>() {
    class ListViewHolder(val binding: RowHargaBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RowHargaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listHarga.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (nama, harga, foto) = listHarga[position]
        holder.binding.itemPhoto.setImageResource(foto)
        holder.binding.itemName.text = nama
        holder.binding.itemHarga.text = harga
    }
}