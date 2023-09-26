package com.example.monitorsismico.main

import androidx.lifecycle.LiveData
import com.example.monitorsismico.Terremoto
import com.example.monitorsismico.api.*
import com.example.monitorsismico.database.TeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: TeDatabase) { //1. pasamos el contexto en ()

    val eqListBd: LiveData<MutableList<Terremoto>> = database.teDao.getTerremotos()

    suspend fun fetchTerremotos() {
        //Corutina en hilo secundario
        return withContext(Dispatchers.IO) {
            val eqJsonResponse : TeJsonResponse = service.getTerremotosUltimaHora()
            val eqList1 :MutableList<Terremoto> = parseEqResult(eqJsonResponse)

            database.teDao.insertAll(eqList1) //terremotos se guardan en la BD

        }
    }

    private fun parseEqResult(eqJsonResponse: TeJsonResponse) :MutableList<Terremoto> {
        //Lista vacia
        val eqList2 :MutableList<Terremoto> = mutableListOf<Terremoto>()
        //
        val featuresList :List<Feature> = eqJsonResponse.features

        for (feature : Feature in featuresList){
            val properties : Properties = feature.properties

            val id :String = feature.id
            val magnitude :Double = properties.mag
            val place :String = properties.place
            val time :Long = properties.time

            val geometry : Geometry = feature.geometry
            val longitude :Double = geometry.longitude
            val latitude :Double = geometry.latitude

            eqList2.add(Terremoto(id, place, magnitude, time, longitude, latitude))
        }
        return eqList2
    }
}