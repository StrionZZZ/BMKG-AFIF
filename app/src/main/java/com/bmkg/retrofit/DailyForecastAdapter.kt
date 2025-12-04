package com.bmkg.retrofit

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmkg.retrofit.model.DailyForecast
import com.bmkg.retrofit.utils.SvgSoftwareLayerSetter
import com.bumptech.glide.Glide

class DailyForecastAdapter(
    private val list: List<DailyForecast>,
    private val context: Context
) : RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvDate: TextView = v.findViewById(R.id.tvDate)
        val tvTempMaxMin: TextView = v.findViewById(R.id.tvTempMaxMin)
        val tvDescDay: TextView = v.findViewById(R.id.tvWeatherDesc)
        val tvRainChance: TextView = v.findViewById(R.id.tvRainChance)
        val ivIconDay: ImageView = v.findViewById(R.id.ivIconDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context)
            .inflate(R.layout.item_daily_forecast, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val d = list[position]

        holder.tvDate.text = d.dateLabel
        holder.tvTempMaxMin.text = "${d.maxTemp}° / ${d.minTemp}°"
        holder.tvDescDay.text = d.description
        holder.tvRainChance.text = "${d.rainChance}% hujan"

        val iconUrl = d.iconUrl.replace(" ", "%20")

        Glide.with(context)
            .`as`(PictureDrawable::class.java)
            .load(iconUrl)
            .into(SvgSoftwareLayerSetter(holder.ivIconDay))
    }

    override fun getItemCount(): Int = list.size
}
