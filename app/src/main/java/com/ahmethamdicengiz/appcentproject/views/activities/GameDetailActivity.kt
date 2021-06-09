package com.ahmethamdicengiz.appcentproject.views.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahmethamdicengiz.appcentproject.R
import com.ahmethamdicengiz.appcentproject.local.FavoriteGameViewModel
import com.ahmethamdicengiz.appcentproject.model.FavoriteGameEntity
import com.ahmethamdicengiz.appcentproject.model.GameDetail
import com.ahmethamdicengiz.appcentproject.viewmodels.GameDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_detail.*
import kotlinx.coroutines.*

class GameDetailActivity : AppCompatActivity() {

    private lateinit var gameDetailViewModel: GameDetailViewModel
    private lateinit var favoriteGameViewModel: FavoriteGameViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)
        val id: String? = intent.getStringExtra("key")
        favoriteGameViewModel = ViewModelProvider(this)[FavoriteGameViewModel::class.java]
        gameDetailViewModel = ViewModelProvider(this)[GameDetailViewModel::class.java]
        checkFavoriteGame(id!!.toInt())


        gameDetailViewModel.getGameDetail(id!!)!!.observe(this, Observer<GameDetail> { gamelist ->
            Picasso.get().load(gamelist.background_image).into(imgItem)
            gameName.text = gamelist.name
            releaseDate.text = "Released Date - " + gamelist.released
            metacriticRate.text = "Metacritic Rate - " + gamelist.metacritic
            gameDescription.text = arrangeString(gamelist.description)

            GlobalScope.launch {
                networkCall()
            }

            button_detail_add_favorite.setOnClickListener {
                val temp = FavoriteGameEntity(
                    gamelist.id,
                    gamelist.name,
                    gamelist.description,
                    gamelist.metacritic,
                    gamelist.released,
                    gamelist.background_image,
                    gamelist.rating
                )
                if (button_detail_add_favorite.isSelected) {
                    button_detail_add_favorite.isSelected = false
                    favoriteGameViewModel.deleteFavoriteGameDetail(temp)
                    button_detail_add_favorite.setImageResource(R.drawable.ic_notlike)
                    Toast.makeText(this, "Removed to Favorites", Toast.LENGTH_SHORT).show()

                } else {
                    button_detail_add_favorite.isSelected = true
                    favoriteGameViewModel.insertFavoriteGameDetail(temp)
                    button_detail_add_favorite.setImageResource(R.drawable.ic_like)
                    Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()

                }
            }
        })

    }

    private fun arrangeString(text: String): String {
        var temp = text
        temp = temp.replace("<p>", "")
        temp = temp.replace("</p>", "")
        temp = temp.replace("<br />", "")


        return temp
    }

    private suspend fun networkCall() {
        withContext(Dispatchers.Default) {
            delay(1000L)
            runOnUiThread {
                progress_gameDetail.visibility = View.INVISIBLE
                scrollView.visibility = View.VISIBLE
                appBar.visibility = View.VISIBLE
            }
        }
    }

    fun checkFavoriteGame(id: Int) {
        favoriteGameViewModel.allFavoriteGameDetail.observe(this, Observer { gamelist ->
            for (i in gamelist.indices) {
                if (gamelist[i].id.equals(id)) {
                    button_detail_add_favorite.setImageResource(R.drawable.ic_like)
                    button_detail_add_favorite.isSelected = true
                }
            }

        })
    }
}