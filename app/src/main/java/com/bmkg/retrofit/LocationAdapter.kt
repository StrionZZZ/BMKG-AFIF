package com.bmkg.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmkg.retrofit.model.Location

class LocationAdapter(
    private val list: List<Location>,
    private val listener: (Location) -> Unit
) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val code: TextView = itemView.findViewById(R.id.tvCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val loc = list[position]
        holder.name.text = loc.name
        holder.code.text = loc.code

        holder.itemView.setOnClickListener {
            listener(loc)
        }
    }

    override fun getItemCount(): Int = list.size
}
