package com.ahmethamdicengiz.appcentproject.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ahmethamdicengiz.appcentproject.api.ApiClient
import com.ahmethamdicengiz.appcentproject.model.GameDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GameDetailRepository {
    private const val API_KEY = "43c3a30270f04a1fa75467c484fb526d"
    val gameDetail = MutableLiveData<GameDetail>()

    fun getGameDetailApiCall(id:String): MutableLiveData<GameDetail> {

        val call = ApiClient.apiInterface.getGameDetail(id.toInt(),API_KEY)

        call.enqueue(object: Callback<GameDetail> {
            override fun onFailure(call: Call<GameDetail>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<GameDetail>,
                response: Response<GameDetail>
            ) {
                Log.v("DEBUG : ", response.body().toString())
                gameDetail.value = response.body()

            }
        })

        return gameDetail
    }
}