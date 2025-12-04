package com.bmkg.retrofit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmkg.retrofit.model.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    private lateinit var rvLokasi: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvLokasi = findViewById(R.id.rvLokasi)
        rvLokasi.layoutManager = LinearLayoutManager(this)

        val list = loadLocations()

        val adapter = LocationAdapter(list) { loc ->
            val intent = Intent(this, WeatherActivity::class.java)
            intent.putExtra("kode_wilayah", loc.code)
            startActivity(intent)
        }

        rvLokasi.adapter = adapter
    }

    private fun loadLocations(): List<Location> {
        return try {
            val inputStream = assets.open("lokasi.json")
            val json = inputStream.bufferedReader().use { it.readText() }

            val gson = Gson()
            val listType: Type = object : TypeToken<List<Location>>() {}.type
            gson.fromJson(json, listType)
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }
}
