package com.ahmethamdicengiz.appcentproject.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmethamdicengiz.appcentproject.R
import com.ahmethamdicengiz.appcentproject.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_viewpager_design.view.*
import kotlin.collections.ArrayList

class GameListViewPagerAdapter(
    private var items: ArrayList<Result>,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<GameListViewPagerAdapter.MyViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Result)
    }

    var gameList = ArrayList<Result>()

    init {
        gameList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_viewpager_design, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = gameList[position]
        Picasso.get().load(currentItem.background_image).into(holder.itemView.imageView)

        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(currentItem)
        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}