package com.ahmethamdicengiz.appcentproject.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ahmethamdicengiz.appcentproject.R
import com.ahmethamdicengiz.appcentproject.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_row_design.view.*
import kotlin.collections.ArrayList

class GameListRecylerViewAdapter(
    private var items: ArrayList<Result>,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<GameListRecylerViewAdapter.MyViewHolder>(), Filterable {

    interface OnClickListener {
        fun onItemClick(position: Result)
    }

    var gameList = ArrayList<Result>()

    init {
        gameList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_design, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return gameList.size;
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = gameList[position]
        holder.itemView.nameOfGame.text = currentItem.name
        holder.itemView.rating.text = "Rate - " + currentItem.rating.toString()
        holder.itemView.date.text = "Released Date - " + currentItem.released
        Picasso.get().load(currentItem.background_image).into(holder.itemView.typeImageView)

        holder.itemView.rowLayout.setOnClickListener {
            onClickListener.onItemClick(currentItem)

        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    gameList = items
                } else {
                    val resultList = ArrayList<Result>()
                    for (row in items) {
                        if (row.name.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    gameList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = gameList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    gameList = results.values as ArrayList<Result>
                    notifyDataSetChanged()
                }

            }

        }
    }
}