package com.example.sub2kotlinexpert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.entity.FavoriteMatch
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.helper.Helper
import kotlinx.android.synthetic.main.item_list_favorite_match.view.*

class FavoriteMatchAdapter : RecyclerView.Adapter<FavoriteMatchAdapter.ViewHolder>(){

    private val listData = ArrayList<FavoriteMatch>()

    fun setListData(data: ArrayList<FavoriteMatch>){
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var onButtonDeleteClickCallback: OnButtonDeleteClickCallback
    private lateinit var onItemClickCallback: OnItemClickCallback<FavoriteMatch>

    fun setOnButtonDeleteClickCallback(onButtonDeleteClickCallback: OnButtonDeleteClickCallback) {
        this.onButtonDeleteClickCallback = onButtonDeleteClickCallback
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback<FavoriteMatch>){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(match: FavoriteMatch){
            with(itemView){
                tv_league.text = match.league
                tv_datetime.text = match.date
                Glide.with(itemView.context)
                    .load(match.homeLogo)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.ic_photo_camera_black_24dp)
                        .error(R.drawable.ic_photo_camera_black_24dp)
                        .override(70,70))
                    .into(iv_home_logo)
                Glide.with(itemView.context)
                    .load(match.awayLogo)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.ic_photo_camera_black_24dp)
                        .error(R.drawable.ic_photo_camera_black_24dp)
                        .override(70,70))
                    .into(iv_away_logo)
                tv_home_team.text = match.homeTeam
                tv_away_team.text = match.awayTeam

                val date = match.date.split("-").reversed().joinToString("-")
                val dateTime = Helper.setTime("$date ${match.time}")
                tv_datetime.text = dateTime
                btn_delete_favorite_match.setOnClickListener{
                    onButtonDeleteClickCallback.onButtonDeleteClicked(match.id, adapterPosition)
                }

                itemView.setOnClickListener{
                    onItemClickCallback.onItemClicked(match)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_favorite_match, parent, false))
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    fun removeItem(position: Int) {
        this.listData.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listData.size)
    }

    interface OnButtonDeleteClickCallback {
        fun onButtonDeleteClicked(id: String, position: Int)
    }


}