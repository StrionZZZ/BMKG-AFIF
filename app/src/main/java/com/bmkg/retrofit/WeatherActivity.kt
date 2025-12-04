package com.bmkg.retrofit

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmkg.retrofit.api.ApiClient
import com.bmkg.retrofit.api.ApiService
import com.bmkg.retrofit.model.CuacaItem
import com.bmkg.retrofit.model.DailyForecast
import com.bmkg.retrofit.model.DataItem
import com.bmkg.retrofit.model.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.min
import kotlin.math.roundToInt

class WeatherActivity : AppCompatActivity() {

    private lateinit var tvLokasi: TextView
    private lateinit var tvSuhu: TextView
    private lateinit var tvCuaca: TextView
    private lateinit var rvJam: RecyclerView
    private lateinit var rvDaily: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        tvLokasi = findViewById(R.id.tvLokasi)
        tvSuhu = findViewById(R.id.tvSuhu)
        tvCuaca = findViewById(R.id.tvCuaca)
        rvJam = findViewById(R.id.rvJam)
        rvDaily = findViewById(R.id.rvDaily)

        rvJam.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvDaily.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val kodeAdm4 = intent.getStringExtra("kode_wilayah")
        if (kodeAdm4 != null) {
            loadWeather(kodeAdm4)
        } else {
            Log.e("API", "kode_wilayah null")
        }
    }

    private fun loadWeather(adm4Code: String) {
        val api = ApiClient.getClient().create(ApiService::class.java)

        api.getWeatherByAdm4(adm4Code).enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if (!response.isSuccessful || response.body() == null) {
                    Log.e(
                        "API",
                        "Response error. code=${response.code()} msg=${response.message()}"
                    )
                    return
                }

                val body = response.body()!!
                val item: DataItem = body.data?.firstOrNull() ?: run {
                    Log.e("API", "Data kosong")
                    return
                }

                val lokasi = item.lokasi
                tvLokasi.text =
                    "${lokasi?.desa ?: "-"}, ${lokasi?.kecamatan ?: ""}"

                // Cuaca pertama
                val first: CuacaItem? =
                    item.cuaca?.firstOrNull()?.firstOrNull()

                tvSuhu.text = "${first?.t ?: 0}°"
                tvCuaca.text = first?.weather_desc ?: "-"

                // List jam
                val jamList = mutableListOf<CuacaItem>()
                item.cuaca?.forEach { inner ->
                    jamList.addAll(inner)
                }

                rvJam.adapter = WeatherHourAdapter(jamList, this@WeatherActivity)

                // Prakiraan 5 hari
                val dailyList = mutableListOf<DailyForecast>()
                val cuacaList = item.cuaca ?: emptyList()
                val maxHari = min(cuacaList.size, 5)

                for (d in 0 until maxHari) {
                    val hariList = cuacaList[d]
                    if (hariList.isEmpty()) continue

                    var maxTemp = Int.MIN_VALUE
                    var minTemp = Int.MAX_VALUE
                    var totalTp = 0.0

                    for (c in hariList) {
                        val t = c.t ?: continue
                        if (t > maxTemp) maxTemp = t
                        if (t < minTemp) minTemp = t
                        totalTp += (c.tp ?: 0.0)
                    }

                    val avgTp = totalTp / hariList.size
                    val rainChance =
                        min(100.0, (avgTp * 20.0)).roundToInt()

                    val rep = hariList[hariList.size / 2]
                    val localDate = rep.local_datetime?.substring(0, 10) ?: "0000-00-00"
                    val dayLabel =
                        "${localDate.substring(8, 10)}/${localDate.substring(5, 7)}"

                    val df = DailyForecast(
                        dateLabel = dayLabel,
                        maxTemp = if (maxTemp == Int.MIN_VALUE) 0 else maxTemp,
                        minTemp = if (minTemp == Int.MAX_VALUE) 0 else minTemp,
                        description = rep.weather_desc ?: "-",
                        iconUrl = rep.image ?: "",
                        rainChance = rainChance
                    )
                    dailyList.add(df)
                }

                rvDaily.adapter =
                    DailyForecastAdapter(dailyList, this@WeatherActivity)
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("API ERROR", t.message ?: "Unknown error")
            }
        })
    }
}
