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
            val eqList : MutableList<Terremoto> = mutableListOf<Terremoto>()
            eqList.add(Terremoto("1","Santiago",2.5, 12222,-2333.333,-1.56666))
            eqList.add(Terremoto("1","La Paz",3.5, 12222,-2333.333,-1.56666))
            eqList.add(Terremoto("1","Montevideo",4.5, 12222,-2333.333,-1.56666))
            eqList.add(Terremoto("1","Buenos Aires",5.5, 12222,-2333.333,-1.56666))
            eqList.add(Terremoto("1","Guayaquil",6.5, 12222,-2333.333,-1.56666))

            eqList
        }
    }
    /*
    override fun onCleared() {
        super.onCleared()
        job.cancel()//finaliza corutina
    }
     */
}