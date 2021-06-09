package com.ahmethamdicengiz.appcentproject.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmethamdicengiz.appcentproject.R
import com.ahmethamdicengiz.appcentproject.model.GameListEntity
import com.ahmethamdicengiz.appcentproject.model.Result
import com.ahmethamdicengiz.appcentproject.viewmodels.GameListViewModel
import com.ahmethamdicengiz.appcentproject.views.adapters.GameListRecylerViewAdapter
import com.ahmethamdicengiz.appcentproject.views.adapters.GameListViewPagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bottom_navigation_bar
import kotlinx.android.synthetic.main.activity_main.empty
import kotlinx.android.synthetic.main.activity_main.game_search
import kotlinx.android.synthetic.main.activity_main.recyclerview

class MainActivity : AppCompatActivity() {
    private var adapterRecyclerView: GameListRecylerViewAdapter? = null
    private var adapterViewPager: GameListViewPagerAdapter? = null
    private var gameListRecyclerView: ArrayList<Result> = ArrayList()
    private var gameListViewPager: ArrayList<Result> = ArrayList()

    private lateinit var gameListViewModel: GameListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameListViewModel = ViewModelProvider(this)[GameListViewModel::class.java]
        bottomNavBar()
        fetchGameList()

    }

    private fun fetchGameList() {
        gameListViewModel.getGameList()!!.observe(this, Observer<GameListEntity> { gamelist ->
            if (gameListRecyclerView.isEmpty()) {
                for (i in gamelist.results.indices) {
                    if (i < 3) {
                        gameListViewPager.add(gamelist.results[i])
                    } else {
                        gameListRecyclerView.add(gamelist.results[i])
                    }
                }
            }

            setRecylcerViewAdapter()
            showEmptyError()
            setViewPagerAdapter()
            searchFilter()

        })
    }

    private fun showEmptyError() {
        adapterRecyclerView!!.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
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
                empty.visibility =
                    (if (adapterRecyclerView!!.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })
        recyclerview.adapter = adapterRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    private fun setRecylcerViewAdapter() {
        adapterRecyclerView = GameListRecylerViewAdapter(
            gameListRecyclerView,
            object : GameListRecylerViewAdapter.OnClickListener {
                override fun onItemClick(position: Result) {
                    val intent = Intent(this@MainActivity, GameDetailActivity::class.java)
                    intent.putExtra("key", position.id)
                    startActivity(intent)

                }

            })
    }

    private fun setViewPagerAdapter() {
        adapterViewPager = GameListViewPagerAdapter(
            gameListViewPager,
            object : GameListViewPagerAdapter.OnClickListener {
                override fun onItemClick(position: Result) {
                    val intent = Intent(this@MainActivity, GameDetailActivity::class.java)
                    intent.putExtra("key", position.id)
                    startActivity(intent)

                }

            })
        viewPager.adapter = adapterViewPager
        worm_dots_indicator.setViewPager2(viewPager)
    }

    private fun searchFilter() {
        game_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.length > 2) {
                    adapterRecyclerView!!.filter.filter(newText)
                    viewPager.visibility = View.GONE
                    worm_dots_indicator.visibility = View.GONE
                } else if (newText.isEmpty()) {
                    adapterRecyclerView!!.filter.filter(newText)
                    viewPager.visibility = View.VISIBLE
                    worm_dots_indicator.visibility = View.VISIBLE

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
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
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
        this.finishAffinity()
    }
}