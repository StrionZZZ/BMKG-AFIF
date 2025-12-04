package com.bmkg.retrofit.model

data class DataItem(
    val lokasi: LokasiModel? = null,
    val cuaca: List<List<CuacaItem>>? = null
)
