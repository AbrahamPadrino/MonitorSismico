package com.example.monitorsismico

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terremotos") //anotación para uso de sql
data class Terremoto(@PrimaryKey val id:String, val lugar: String, val magnitud: Double, val time: Long,
                val longitud: Double, val latitud: Double) {
}