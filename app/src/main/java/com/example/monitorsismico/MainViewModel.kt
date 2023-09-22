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

    //instanciando el la clase repositorio
    private val repository = MainRepository()
    init {
        /* corutineScope.launch {
        * */
            //Corutina en hilo principal

            viewModelScope.launch {
                _eqList.value = repository.fetchTerremotos()
            }
    }
    /*
    override fun onCleared() {
        super.onCleared()
        job.cancel()//finaliza corutina
    }
     */
}