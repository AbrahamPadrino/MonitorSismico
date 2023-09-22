package com.example.monitorsismico

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository {
    suspend fun fetchTerremotos(): MutableList<Terremoto> {
        //Corutina en hilo secundario
        return withContext(Dispatchers.IO) {
            val eqJsonResponse :TeJsonResponse= service.getTerremotosUltimaHora()
            val eqList1 :MutableList<Terremoto> = parseEqResult(eqJsonResponse)
            eqList1
        }
    }

    private fun parseEqResult(eqJsonResponse: TeJsonResponse) :MutableList<Terremoto> {
        //Lista vacia
        val eqList2 :MutableList<Terremoto> = mutableListOf<Terremoto>()
        //
        val featuresList :List<Feature> = eqJsonResponse.features

        for (feature :Feature in featuresList){
            val properties :Properties = feature.properties

            val id :String = feature.id
            val magnitude :Double = properties.mag
            val place :String = properties.place
            val time :Long = properties.time

            val geometry :Geometry = feature.geometry
            val longitude :Double = geometry.longitude
            val latitude :Double = geometry.latitude

            eqList2.add(Terremoto(id, place, magnitude, time, longitude, latitude))
        }
        return eqList2
    }
}