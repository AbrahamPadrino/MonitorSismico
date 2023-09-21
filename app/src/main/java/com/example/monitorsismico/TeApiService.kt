package com.example.monitorsismico

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

    interface TeApiService {
        @GET("all_hour.geojson")
        suspend fun getTerremotosUltimaHora(): String
    }
    //crea el servicio de retrofit con la  interfaz creada
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
        .addConverterFactory(ScalarsConverterFactory.create()) //convierte los datos descargados a Strings (require dependencia)
        .build()

    var service: TeApiService = retrofit.create(TeApiService::class.java)
