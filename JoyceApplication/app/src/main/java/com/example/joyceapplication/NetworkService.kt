package com.example.joyceapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("getUlfptcaAlarmInfo") //이렇게 하면 getUlfptcaAlarmInfo뒤에 ? 붙임
    fun getJsonList(
        @Query("year") year:Int,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("returnType") returnType:String,
        @Query("serviceKey") serviceKey:String
    ) : Call<JsonResponse> //import retrofit2.Call 해줘야 함

    @GET("getUlfptcaAlarmInfo")
    fun getXmlList(
        @Query("year") year:Int,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("returnType") returnType:String,
        @Query("serviceKey") serviceKey:String
    ) : Call<XmlResponse>
}