package com.example.skripsiuuu.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skripsiuuu.data.response.DataRiwayat

@Database(
    entities = [DataRiwayat::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class RiwayatDatabase: RoomDatabase(){
    abstract fun riwayatDao() : RiwayatDao
    abstract fun remoteKeysDao() : RemoteKeysDao

    companion object{
        @Volatile
        private var INSTANCE: RiwayatDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): RiwayatDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RiwayatDatabase::class.java, "riwayat_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}
