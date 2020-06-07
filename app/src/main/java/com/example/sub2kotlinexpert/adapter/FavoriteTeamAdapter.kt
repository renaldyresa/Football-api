package com.example.sub2kotlinexpert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.entity.Team
import kotlinx.android.synthetic.main.item_list_favorite_team.view.*

class FavoriteTeamAdapter: RecyclerView.Adapter<FavoriteTeamAdapter.ViewHolder>() {

    private val listTeam = ArrayList<Team>()

    fun setListTeam(data: ArrayList<Team>){
        listTeam.clear()
        listTeam.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var onButtonDeleteClickCallback: OnButtonDeleteClickCallback
    private lateinit var onItemClickCallback: OnItemClickCallback<Team>

    fun setOnButtonDeleteClickCallback(onButtonDeleteClickCallback: OnButtonDeleteClickCallback) {
        this.onButtonDeleteClickCallback = onButtonDeleteClickCallback
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback<Team>){
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(team: Team){
            with(itemView){
                tv_team_name.text = team.name
                Glide.with(itemView.context)
                    .load(team.logo)
                    .apply(
                        RequestOptions()
                        .placeholder(R.drawable.ic_photo_camera_black_24dp)
                        .error(R.drawable.ic_photo_camera_black_24dp)
                        .override(150,150))
                    .into(iv_team_logo)
                tv_formed_year.text = team.formedYear
                tv_stadium.text = team.stadiumName
                btn_delete_favorite_team.setOnClickListener{
                    onButtonDeleteClickCallback.onButtonDeleteClicked(team.idTeam, adapterPosition)
                }

                this.setOnClickListener{
                    onItemClickCallback.onItemClicked(team)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_favorite_team, parent, false))
    }

    override fun getItemCount(): Int = listTeam.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTeam[position])
    }

    fun removeItem(position: Int) {
        this.listTeam.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listTeam.size)
    }

    interface OnButtonDeleteClickCallback {
        fun onButtonDeleteClicked(id: String, position: Int)
    }

}