package com.example.sub2kotlinexpert.view.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.FavoriteTeamAdapter
import com.example.sub2kotlinexpert.adapter.OnItemClickCallback
import com.example.sub2kotlinexpert.data.db.FavoriteTeamHelper
import com.example.sub2kotlinexpert.entity.Team
import com.example.sub2kotlinexpert.helper.Helper
import com.example.sub2kotlinexpert.view.teamdetail.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_team_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamFavoriteFragment(context: Context) : Fragment() {
    private val mContext = context
    private lateinit var adapter : FavoriteTeamAdapter
    private lateinit var dbHelper : FavoriteTeamHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = FavoriteTeamHelper.getInstance(mContext)
        dbHelper.open()
        return inflater.inflate(R.layout.fragment_team_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerList()
        loadFavoriteMatchAsync()
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteMatchAsync()
    }

    private fun loadFavoriteMatchAsync(){
        GlobalScope.launch(Dispatchers.Main){
            pb_fm_favorite_team.visibility = View.VISIBLE
            val deferredMatch = async(Dispatchers.IO){
                val cursor = dbHelper.queryAll()
                Helper.mapCursorToArrayListTeam(cursor)
            }

            val listTeam = deferredMatch.await()
            pb_fm_favorite_team.visibility = View.GONE
            if (listTeam.size > 0){
                adapter.setListTeam(listTeam)
            }else{
                adapter.setListTeam(ArrayList())
                tv_info.text = resources.getString(R.string.txt_no_data)
            }
        }
    }

    private fun showRecyclerList(){
        rv_favorite_team.layoutManager = LinearLayoutManager(activity)
        rv_favorite_team.setHasFixedSize(true)
        adapter = FavoriteTeamAdapter()
        rv_favorite_team.adapter = adapter
        adapter.setOnButtonDeleteClickCallback(object : FavoriteTeamAdapter.OnButtonDeleteClickCallback {
            override fun onButtonDeleteClicked(id: String, position: Int) {
                deleteSelectedItem(id, position)
            }
        })
        adapter.setOnItemClickCallback(object : OnItemClickCallback<Team> {
            override fun onItemClicked(data: Team) {
                val intent = Intent(activity, TeamDetailActivity::class.java)
                intent.putExtra(TeamDetailActivity.EXTRA_ID_LEAGUE, data.idTeam)
                startActivity(intent)
            }
        })
    }

    private fun deleteSelectedItem(id_match: String, position: Int) {
        val result = dbHelper.deleteById(id_match)
        if (result > 0){
            adapter.removeItem(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dbHelper.isOpen())
            dbHelper.close()

    }

}
