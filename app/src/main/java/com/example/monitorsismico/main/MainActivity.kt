package com.example.monitorsismico.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitorsismico.Terremoto
import com.example.monitorsismico.api.ApiResponseStatus
import com.example.monitorsismico.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Establecer el layoutManager
        binding.eqRecycler.layoutManager = LinearLayoutManager(this)
        //Observer
        val viewModel: MainViewModel = ViewModelProvider(this,
            MainViewModelFactory(application))[MainViewModel::class.java]
            //application = contexto
        //Asignar el adapter al RecyclerView
        val adapter = TeAdapter(this)
        binding.eqRecycler.adapter = adapter
        viewModel.eqList.observe(this, Observer {
            eqList: MutableList<Terremoto> ->
            adapter.submitList(eqList)
            //Mostrar mensaje de lista vacia.
            handleEmptyView(eqList, binding)
        })
        //Observe para el status de la carga de terremotos
        viewModel.status.observe(this, Observer {
            apiResponseStatus : ApiResponseStatus ->
            if (apiResponseStatus == ApiResponseStatus.LOADING) {
                binding.loadingWheel.visibility = View.VISIBLE
            } else if (apiResponseStatus == ApiResponseStatus.DONE) {
                binding.loadingWheel.visibility = View.GONE
            } else if (apiResponseStatus == ApiResponseStatus.NOT_INTERNET) {
                binding.loadingWheel.visibility = View.GONE
            }
        })

        adapter.onItemClickListener = {
            Toast.makeText(this, it.lugar, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEmptyView(
        eqList: MutableList<Terremoto>,
        binding: ActivityMainBinding
    ) {
        if (eqList.isEmpty()) {
            binding.eqEmptyView.visibility = View.VISIBLE
        } else {
            binding.eqEmptyView.visibility = View.GONE
        }
    }
}