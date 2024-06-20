package com.example.joyceapplication

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2024&pageNo=1&numOfRows=10&returnType=json&serviceKey=O3hl%2Fg6Dtviy%2BFkpasNjvSNu29%2FInnSiaEpLCXwtw8pSxQdlIiCoVyblcsV3zeckJK3lECAqcJrCE0YO7imieg%3D%3D



class RetrofitConnection {
    companion object{
        private const val BASE_URL = "https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/"

        var jsonNetServ : NetworkService

        val jsonRetrofit : Retrofit

            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val xmlNetServ : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        init{
            jsonNetServ = jsonRetrofit.create(NetworkService::class.java)
            xmlNetServ = xmlRetrofit.create(NetworkService::class.java)
        }
    }

}