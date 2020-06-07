package com.example.sub2kotlinexpert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub2kotlinexpert.entity.League
import com.example.sub2kotlinexpert.R

class LeagueAdapter(private val listLeague: ArrayList<League>, private val listener: (League) -> Unit):  RecyclerView.Adapter<LeagueAdapter.GridViewHolder>(){

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tv_name_league)
        private val image: ImageView = itemView.findViewById(R.id.iv_logo_league)
        fun bind(item : League){
            name.text = item.name
            Glide.with(itemView.context)
                .load(item.image)
                .apply(RequestOptions().override(80,80))
                .into(image)
            itemView.setOnClickListener{ listener(item)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_grid_league, parent, false))
    }

    override fun getItemCount() = listLeague.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(listLeague[position])
    }

}