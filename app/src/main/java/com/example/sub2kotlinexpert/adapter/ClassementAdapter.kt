package com.example.sub2kotlinexpert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.entity.Classement
import kotlinx.android.synthetic.main.item_list_classement.view.*

class ClassementAdapter: RecyclerView.Adapter<ClassementAdapter.ViewHolder>() {

    private val listClassement = ArrayList<Classement>()

    fun  setListClassement(data: ArrayList<Classement>){
        listClassement.clear()
        listClassement.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var onItemClickCallback: OnItemClickCallback<Classement>

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback<Classement>){
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(classement: Classement){
            with(itemView){
                tv_number.text = (adapterPosition+1).toString()
                Glide.with(itemView)
                    .load(classement.logo)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.ic_photo_camera_black_24dp)
                        .error(R.drawable.ic_photo_camera_black_24dp)
                        .override(100,100))
                    .into(iv_team_logo)
                tv_team_name.text = classement.name
                tv_win.text = classement.win.toString()
                tv_draw.text = classement.draw.toString()
                tv_loss.text = classement.loss.toString()
                tv_g_diff.text = classement.goalsDifference.toString()
                tv_total.text = classement.total.toString()
                this.setOnClickListener{
                    onItemClickCallback.onItemClicked(listClassement[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_classement, parent, false))
    }

    override fun getItemCount(): Int = listClassement.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listClassement[position])
    }

}