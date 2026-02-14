package com.example.tarea_grupal_12

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Tasa(val from: String, val rate: Double)

class TasasAdapter(private val tasas: List<Tasa>) : RecyclerView.Adapter<TasasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTasaInfo: TextView = view.findViewById(R.id.textViewTasaInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tasa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tasa = tasas[position]
        holder.textViewTasaInfo.text = "1 USD = ${tasa.rate} ${tasa.from}"
    }

    override fun getItemCount() = tasas.size
}