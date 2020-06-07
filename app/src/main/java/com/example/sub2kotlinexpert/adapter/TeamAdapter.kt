package com.example.sub2kotlinexpert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.entity.Team
import kotlinx.android.synthetic.main.item_list_team.view.*

class TeamAdapter: RecyclerView.Adapter<TeamAdapter.ViewModel>() {

    private val listTeam = ArrayList<Team>()

    fun setListTeam(data: ArrayList<Team>){
        listTeam.clear()
        listTeam.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var onItemClickCallback: OnItemClickCallback<Team>

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback<Team>){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewModel(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(team: Team){
            with(itemView){
                Glide.with(itemView)
                    .load(team.logo)
                    .apply(
                        RequestOptions()
                        .placeholder(R.drawable.ic_photo_camera_black_24dp)
                        .error(R.drawable.ic_photo_camera_black_24dp)
                        .override(100,100))
                    .into(iv_team_logo)
                tv_team_name.text = team.name
                tv_formed_year.text = team.formedYear
                tv_txt_stadium.text = team.stadiumName
                this.setOnClickListener{
                    onItemClickCallback.onItemClicked(listTeam[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        return ViewModel(LayoutInflater.from(parent.context).inflate(R.layout.item_list_team, parent, false))
    }

    override fun getItemCount(): Int = listTeam.size

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        holder.bind(listTeam[position])
    }


}