package com.ahmethamdicengiz.appcentproject.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ahmethamdicengiz.appcentproject.api.ApiClient
import com.ahmethamdicengiz.appcentproject.model.GameListEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GameListRepository {
    private const val API_KEY = "43c3a30270f04a1fa75467c484fb526d"
    val gameList = MutableLiveData<GameListEntity>()

    fun getGameListApiCall(): MutableLiveData<GameListEntity> {

        val call = ApiClient.apiInterface.getGamesList(API_KEY)

        call.enqueue(object : Callback<GameListEntity> {
            override fun onFailure(call: Call<GameListEntity>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<GameListEntity>,
                response: Response<GameListEntity>
            ) {
                Log.v("DEBUG : ", response.body().toString())
                gameList.value = response.body()

            }
        })

        return gameList
    }
}