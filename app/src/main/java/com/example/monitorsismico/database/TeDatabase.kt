package com.example.monitorsismico.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.monitorsismico.Terremoto

@Database(entities = [Terremoto::class], version = 1)
abstract class TeDatabase: RoomDatabase() {
    abstract val teDao: TeDao
}
//Singleton
//impedir que se cree mas de una bd en la app
private lateinit var INSTANCE: TeDatabase

fun getDatabase(context: Context): TeDatabase {
    synchronized(TeDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, TeDatabase::class.java,
            "terremoto_db").build()
        }
        return INSTANCE
    }
}
//