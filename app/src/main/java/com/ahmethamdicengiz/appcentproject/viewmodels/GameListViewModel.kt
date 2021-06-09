package com.ahmethamdicengiz.appcentproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmethamdicengiz.appcentproject.model.GameListEntity

class GameListViewModel : ViewModel() {
    var gamelistLiveData: MutableLiveData<GameListEntity>? = null

    fun getGameList(): LiveData<GameListEntity>? {
        gamelistLiveData = GameListRepository.getGameListApiCall()
        return gamelistLiveData
    }
}

