package com.example.monitorsismico.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.monitorsismico.Terremoto

@Dao
interface TeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //inserta el nuevo registro y reemplaza en caso tenga el mismo id
    fun insertAll(eqList: MutableList<Terremoto>)

    @Query("SELECT * FROM terremotos")
    fun getTerremotos(): LiveData<MutableList<Terremoto>>
}