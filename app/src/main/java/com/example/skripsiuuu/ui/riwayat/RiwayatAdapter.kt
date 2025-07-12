package com.example.skripsiuuu.ui.riwayat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import com.example.skripsiuuu.data.response.DataRiwayat
import com.example.skripsiuuu.databinding.ItemRiwayatBinding

class RiwayatAdapter:
    PagingDataAdapter<DataRiwayat, RiwayatAdapter.ViewHolder>(DIFF_CALLBACK){

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val data = getItem(position)
//        data?.let { holder.bind(it) }
        val data = getItem(position)
        if (data != null) {
            Log.d("RiwayatAdapter", "Binding data: $data")
            holder.bind(data)
        } else {
            Log.e("RiwayatAdapter", "Data at position $position is null")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(private val binding: ItemRiwayatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataRiwayat) {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

            val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val outputTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

            val dateText = try {
                val parsedDate = inputFormat.parse(data.waktu)
                val formattedDate = outputDateFormat.format(parsedDate!!)
//                val formattedTime = outputTimeFormat.format(parsedDate)
                "$formattedDate"
//                "Tanggal: $formattedDate\nPukul: $formattedTime"
            } catch (e: Exception) {
                e.printStackTrace()
                "Invalid date"
            }

            val timeText = try {
                val parsedDate = inputFormat.parse(data.waktu)
//                val formattedDate = outputDateFormat.format(parsedDate!!)
                val formattedTime = outputTimeFormat.format(parsedDate)
                "$formattedTime"
//                "Tanggal: $formattedDate\nPukul: $formattedTime"
            } catch (e: Exception) {
                e.printStackTrace()
                "Invalid Time"
            }

            binding.date.text = dateText
            binding.time.text = timeText
            binding.glassNumber.text = data.glass.toString()
            binding.metalNumber.text = data.metal.toString()
            binding.paperNumber.text = String.format("%.2f", data.paper)

            binding.plasticNumber.text = data.plastic.toString()

            val balance = (data.glass * 5) + (data.metal * 8) + (data.paper * 0.002) + (data.plastic * 10)
            binding.balance.text = String.format("%.2f", balance)

            onItemClickCallback?.let { callback ->
                itemView.setOnClickListener {
                    callback.onItemClicked(data)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(riwayat: DataRiwayat)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataRiwayat>() {
            override fun areItemsTheSame(oldItem: DataRiwayat, newItem: DataRiwayat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataRiwayat, newItem: DataRiwayat): Boolean {
                return oldItem == newItem
            }
        }
    }

}


