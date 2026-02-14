package com.example.tarea_grupal_12

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Conversion(val from: String, val to: String, val amount: Double, val result: Double, val date: String)

class HistorialAdapter(private val conversiones: List<Conversion>) : RecyclerView.Adapter<HistorialAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewConversion: TextView = view.findViewById(R.id.textViewConversion)
        val textViewFecha: TextView = view.findViewById(R.id.textViewFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conversion = conversiones[position]
        holder.textViewConversion.text = "${conversion.amount} ${conversion.from} -> ${String.format("%.2f", conversion.result)} ${conversion.to}"
        holder.textViewFecha.text = conversion.date
    }

    override fun getItemCount() = conversiones.size
}