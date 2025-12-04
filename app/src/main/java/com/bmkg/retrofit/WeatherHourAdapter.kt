package com.bmkg.retrofit

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmkg.retrofit.model.CuacaItem
import com.bmkg.retrofit.utils.SvgSoftwareLayerSetter
import com.bumptech.glide.Glide

class WeatherHourAdapter(
    private val list: List<CuacaItem>,
    private val context: Context
) : RecyclerView.Adapter<WeatherHourAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val jam: TextView = v.findViewById(R.id.tvJam)
        val suhu: TextView = v.findViewById(R.id.tvSuhu)
        val desc: TextView = v.findViewById(R.id.tvDesc)
        val icon: ImageView = v.findViewById(R.id.ivIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context)
            .inflate(R.layout.item_weather_hour, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val c = list[position]

        holder.jam.text = c.getHour()
        holder.suhu.text = "${c.t ?: 0}°"
        holder.desc.text = c.weather_desc ?: "-"

        val iconUrl = (c.image ?: "").replace(" ", "%20")

        Glide.with(context)
            .`as`(PictureDrawable::class.java)
            .load(iconUrl)
            .into(SvgSoftwareLayerSetter(holder.icon))
    }

    override fun getItemCount(): Int = list.size
}
