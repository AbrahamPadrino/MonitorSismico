package com.example.monitorsismico.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//Codigo Boilerplate
class MainViewModelFactory(private val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}
//1.3 pasar el Contexto (application) al viewModel en MainActivity