package com.example.skripsiuuu.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GetRiwayatResponse(
    @field:SerializedName("data")
    val dataRiwayat: List<DataRiwayat>
)
@Entity(tableName = "riwayat")
data class DataRiwayat(
    @field:SerializedName("glass")
    val glass: Float,

    @field:SerializedName("metal")
    val metal: Float,

    @field:SerializedName("plastic")
    val plastic: Float,

    @field:SerializedName("paper")
    val paper: Float,

    @field:SerializedName("waktu")
    val waktu: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String
)
