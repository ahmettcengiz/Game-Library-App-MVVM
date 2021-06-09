package com.ahmethamdicengiz.appcentproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmethamdicengiz.appcentproject.model.GameDetail

class GameDetailViewModel : ViewModel() {
    var gameDetailLiveData: MutableLiveData<GameDetail>? = null

    fun getGameDetail(id: String): LiveData<GameDetail>? {
        gameDetailLiveData = GameDetailRepository.getGameDetailApiCall(id)
        return gameDetailLiveData
    }
}