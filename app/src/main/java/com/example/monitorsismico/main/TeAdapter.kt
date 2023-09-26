package com.example.monitorsismico.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.monitorsismico.R
import com.example.monitorsismico.Terremoto
import com.example.monitorsismico.databinding.EqListItemBinding

class TeAdapter(private val context: Context): ListAdapter<Terremoto, TeAdapter.TeViewHolder>(
    DiffCallback
) {

    //permite al adapter conocer modificaciones en los items.
    companion object DiffCallback: DiffUtil.ItemCallback<Terremoto>() {
        override fun areItemsTheSame(oldItem: Terremoto, newItem: Terremoto): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Terremoto, newItem: Terremoto): Boolean {
            return oldItem == newItem
        }
    }
    //

    lateinit var onItemClickListener:(Terremoto) ->Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeViewHolder {
        val binding: EqListItemBinding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))
        return TeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeViewHolder, position: Int) {
        val terremoto: Terremoto = getItem(position)
        holder.bind(terremoto)
    }

    inner class TeViewHolder(private val binding: EqListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(terremoto: Terremoto) {
            binding.eqMagnitudTxt.text = context.getString(R.string.magnitude_format, terremoto.magnitud)
            binding.eqPlaceText.text = terremoto.lugar
            //pasar el terremoto de la fila seleccionada.
            binding.root.setOnClickListener{
                onItemClickListener(terremoto)
            }

        }
    }
}