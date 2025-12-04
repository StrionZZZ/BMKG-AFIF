package com.bmkg.retrofit.model

data class LokasiModel(
    val adm1: String? = null,
    val adm2: String? = null,
    val adm3: String? = null,
    val adm4: String? = null,
    val provinsi: String? = null,
    val kotkab: String? = null,
    val kecamatan: String? = null,
    val desa: String? = null,
    val lon: Double? = null,
    val lat: Double? = null,
    val timezone: String? = null
)
