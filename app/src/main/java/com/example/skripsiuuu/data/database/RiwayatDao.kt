package com.example.skripsiuuu.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.skripsiuuu.data.response.DataRiwayat

@Dao
interface RiwayatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRiwayat(riwayat: List<DataRiwayat>)

    @Query("SELECT * FROM riwayat")
    fun getRiwayat(): PagingSource<Int, DataRiwayat>

    @Query("DELETE FROM riwayat")
    suspend fun deleteAll()
}