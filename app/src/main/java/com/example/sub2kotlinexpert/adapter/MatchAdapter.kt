package com.example.sub2kotlinexpert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.helper.Helper
import kotlinx.android.synthetic.main.item_list_match.view.*
import kotlin.collections.ArrayList

class MatchAdapter: RecyclerView.Adapter<MatchAdapter.ViewHolder>(){

    private val listMatches  = ArrayList<Match>()

    fun setListMatches(data: ArrayList<Match>){
        listMatches.clear()
        listMatches.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var onItemClickCallback: OnItemClickCallback<Match>

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback<Match>){
        this.onItemClickCallback = onItemClickCallback
    }



    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(match: Match){
            with(itemView){
                iv_logo_home_team.setImageResource(R.drawable.ic_photo_camera_black_24dp)
                iv_logo_away_team.setImageResource(R.drawable.ic_photo_camera_black_24dp)
                tv_txt_vs.text = match.name
                tv_d_league_name.text = match.league
                tv_home_score.text = if (match.homeScore.isEmpty() || match.homeScore == "null") "-" else match.homeScore
                tv_away_score.text = if(match.awayScore.isEmpty() || match.awayScore == "null") "-" else match.awayScore
                val date = match.date.split("-").reversed().joinToString("-")
                val dateTime = Helper.setTime("$date ${match.time}")
                tv_dateTime.text = dateTime
                if (match.homeLogo.isNotEmpty()){
                    Glide.with(itemView)
                        .load(match.homeLogo)
                        .apply(RequestOptions()
                            .placeholder(R.drawable.ic_photo_camera_black_24dp)
                            .error(R.drawable.ic_photo_camera_black_24dp)
                            .override(100,100))
                        .into(iv_logo_home_team)
                }
                if (match.awayLogo.isNotEmpty()){
                    Glide.with(itemView)
                        .load(match.awayLogo)
                        .apply(RequestOptions()
                            .placeholder(R.drawable.ic_photo_camera_black_24dp)
                            .error(R.drawable.ic_photo_camera_black_24dp)
                            .override(100,100))
                        .into(iv_logo_away_team)
                }
                itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listMatches[adapterPosition])}
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_match, parent, false))
    }

    override fun getItemCount(): Int {
        return listMatches.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMatches[position])
    }

}