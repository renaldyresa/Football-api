package com.example.sub2kotlinexpert.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.LeagueAdapter
import com.example.sub2kotlinexpert.entity.League
import com.example.sub2kotlinexpert.model.LeagueData
import com.example.sub2kotlinexpert.view.leaguedetail.LeagueDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = LeagueData.listData
        showRecycler(list)
    }

    private fun showRecycler(list: ArrayList<League>){
        rv_league.layoutManager = GridLayoutManager(activity, 2)
        val leagueAdapter =
            LeagueAdapter(list) {
                val intent = Intent(activity, LeagueDetailActivity::class.java)
                intent.putExtra(LeagueDetailActivity.EXTRA_LEAGUE, it)
                startActivity(intent)
            }
        rv_league.adapter = leagueAdapter
    }

}
