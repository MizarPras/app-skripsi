package com.example.skripsiuuu.ui.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Harga(
    val nama: String,
    val harga: String,
    val foto: Int,
): Parcelable
