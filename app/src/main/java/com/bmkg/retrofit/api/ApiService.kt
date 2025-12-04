package com.bmkg.retrofit.api

import com.bmkg.retrofit.model.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("prakiraan-cuaca")
    fun getWeatherByAdm4(
        @Query("adm4") adm4: String
    ): Call<ResponseData>
}
