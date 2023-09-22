package com.example.monitorsismico

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {
    /* otro forma para corutina
    private val job = Job()
    private val corutineScope = CoroutineScope(Dispatchers.Main + job) //alcance
    */
    //Propiedad de copia de seguridad
    private var _eqList = MutableLiveData<MutableList<Terremoto>>()
    val eqList: LiveData<MutableList<Terremoto>>
        get() = _eqList
    //
    init {
        /* corutineScope.launch {
        * */
            viewModelScope.launch {
                _eqList.value = fetchTerremotos()
            }
    }

    private suspend fun fetchTerremotos(): MutableList<Terremoto> {
        //hilo secundario
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
    /*
    override fun onCleared() {
        super.onCleared()
        job.cancel()//finaliza corutina
    }
     */
}