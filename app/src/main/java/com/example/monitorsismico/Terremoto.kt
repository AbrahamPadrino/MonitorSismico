package com.example.monitorsismico

data class Terremoto(val id:String, val lugar: String, val magnitud: Double, val time: Long,
                val longitud: Double, val latitud: Double) {
}