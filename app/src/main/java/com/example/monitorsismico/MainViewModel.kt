package com.example.monitorsismico

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject

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
            val eqListString : String = service.getTerremotosUltimaHora()
            val eqList1 :MutableList<Terremoto> = parseEqResult(eqListString)
            eqList1
        }
    }

    private fun parseEqResult(eqListString: String) :MutableList<Terremoto> {
        val eqJsonObject = JSONObject(eqListString)
        val featuresJsonArray : JSONArray = eqJsonObject.getJSONArray("features")

        //Lista vacia
        val eqList2 :MutableList<Terremoto> = mutableListOf<Terremoto>()
        //

        for (i :Int in 0 until featuresJsonArray.length()) {
            val featuresJsonObject :JSONObject = featuresJsonArray[i] as JSONObject
            val id : String = featuresJsonObject.getString("id")

            val propertiesJsonObject :JSONObject = featuresJsonObject.getJSONObject("properties")
            val magnitude :Double = propertiesJsonObject.getDouble("mag")
            val place :String = propertiesJsonObject.getString("place")
            val time :Long = propertiesJsonObject.getLong("time")

            val geometryJsonObject :JSONObject = featuresJsonObject.getJSONObject("geometry")
            val coordinatesJsonArray :JSONArray = geometryJsonObject.getJSONArray("coordinates")
            val longitude :Double = coordinatesJsonArray.getDouble(0)
            val latitude :Double = coordinatesJsonArray.getDouble(1)

            val terremoto = Terremoto(id, place, magnitude, time, longitude, latitude)
            eqList2.add(terremoto)
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