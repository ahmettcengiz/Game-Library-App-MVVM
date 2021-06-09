package com.ahmethamdicengiz.appcentproject.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmethamdicengiz.appcentproject.R
import com.ahmethamdicengiz.appcentproject.local.FavoriteGameViewModel
import com.ahmethamdicengiz.appcentproject.model.FavoriteGameEntity
import com.ahmethamdicengiz.appcentproject.views.adapters.FavoriteActivityRecylerViewAdapter
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_favorite.bottom_navigation_bar
import kotlinx.android.synthetic.main.activity_favorite.game_search
import kotlinx.android.synthetic.main.activity_favorite.recyclerview

class FavoriteActivity : AppCompatActivity() {
    private var adapter: FavoriteActivityRecylerViewAdapter? = null
    private lateinit var favoriteGameViewModel: FavoriteGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        favoriteGameViewModel = ViewModelProvider(this)[FavoriteGameViewModel::class.java]
        bottomNavBar()
        fetchFavorites()

    }

    private fun fetchFavorites() {
        favoriteGameViewModel.allFavoriteGameDetail.observe(this, Observer { gamelist ->
            adapter = FavoriteActivityRecylerViewAdapter(
                gamelist,
                object : FavoriteActivityRecylerViewAdapter.OnClickListener {

                    override fun onItemClick(position: FavoriteGameEntity) {
                        val intent = Intent(this@FavoriteActivity, GameDetailActivity::class.java)
                        intent.putExtra("key", position.id.toString())
                        startActivity(intent)

                    }

                })

            showEmptyError()
            searchFilter()


        })
    }

    private fun showEmptyError() {
        adapter!!.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                empty.visibility = (if (adapter!!.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }


    private fun searchFilter() {
        game_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.length > 2) {
                    adapter!!.filter.filter(newText)

                } else if (newText.isEmpty()) {
                    adapter!!.filter.filter(newText)
                }
                return false
            }
        })
    }

    private fun bottomNavBar() {
        bottom_navigation_bar.selectedItemId = R.id.navigation_homePage
        bottom_navigation_bar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_homePage -> {
                    val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}