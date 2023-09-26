package com.example.monitorsismico.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.monitorsismico.api.ApiResponseStatus
import com.example.monitorsismico.database.getDatabase
import kotlinx.coroutines.*
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(application: Application): AndroidViewModel(application) {
    /* otro forma para corutina
    private val job = Job()
    private val corutineScope = CoroutineScope(Dispatchers.Main + job) //alcance
    */

    //instanciando la clase repositorio
    private val database = getDatabase(application) //1.2 instancia a la bd y se pasa "application"como contexto al repository
    private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status

    val eqList = repository.eqListBd

    init {
            viewModelScope.launch {
                try {
                    _status.value = ApiResponseStatus.LOADING
                    repository.fetchTerremotos()
                    _status.value = ApiResponseStatus.DONE
                } catch (e:UnknownHostException) {
                    _status.value = ApiResponseStatus.NOT_INTERNET
                    Log.d(TAG,"No internet connection",e)
                }

            }
    }
}