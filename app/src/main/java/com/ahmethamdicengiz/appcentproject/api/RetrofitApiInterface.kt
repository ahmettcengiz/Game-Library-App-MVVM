package com.ahmethamdicengiz.appcentproject.api

import com.ahmethamdicengiz.appcentproject.model.GameDetail
import com.ahmethamdicengiz.appcentproject.model.GameListEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiInterface {

    @GET("games")
    fun getGamesList(
        @Query("key") apiKey: String
    ): Call<GameListEntity>

    @GET("games/{id}")
    fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): Call<GameDetail>


}