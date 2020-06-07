package com.example.sub2kotlinexpert.view.leaguedetail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.MatchAdapter
import com.example.sub2kotlinexpert.adapter.OnItemClickCallback
import com.example.sub2kotlinexpert.entity.League
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.model.leaguedetail.EventNextMatchModel
import com.example.sub2kotlinexpert.model.logo.TeamLogoModel
import com.example.sub2kotlinexpert.view.matchdetail.MatchDetailActivity
import com.example.sub2kotlinexpert.viewmodel.leaguedetail.LeagueNextMatchViewModel
import com.example.sub2kotlinexpert.viewmodel.leaguedetail.LeaguePreviousMatchViewModel
import kotlinx.android.synthetic.main.fragment_league_detail.*

class LeagueDetailFragment : Fragment() {

    private lateinit var league: League
    private lateinit var nextAdapter: MatchAdapter
    private lateinit var nextMatchModel: LeagueNextMatchViewModel
    private lateinit var previousAdapter: MatchAdapter
    private lateinit var previousMatchModel: LeaguePreviousMatchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        nextAdapter = MatchAdapter()
        nextMatchModel = ViewModelProvider(this, LeagueNextMatchViewModel.ViewModelFactory(EventNextMatchModel(), TeamLogoModel())).get(
            LeagueNextMatchViewModel::class.java)

        previousAdapter = MatchAdapter()
        previousMatchModel = ViewModelProvider(this, LeaguePreviousMatchViewModel.viewModelProvider).get(
            LeaguePreviousMatchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_league_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<League>(LeagueDetailActivity.EXTRA_LEAGUE)?.let {
            league = it
        }
        setupViewModel()
        setupUi()
    }

    private fun setupViewModel(){
        nextMatchModel.setEventNextMatch(league.id.toString())
        nextMatchModel.getEventNextMatch().observe(this, Observer {
            nextAdapter.setListMatches(it)
        })
        nextMatchModel.isViewLoading.observe(this, isViewLoadingNextObserver)
        nextMatchModel.onMessageError.observe(this, isOnMessageErrorNextObserver)
        nextMatchModel.isEmptyList.observe(this, isEmptyListNextObserver)

        previousMatchModel.setEventPreviousMatch(league.id.toString())
        previousMatchModel.getEventPreviousMatch().observe(this, Observer {
            previousAdapter.setListMatches(it)
        })
        previousMatchModel.isViewLoading.observe(this, isViewLoadingPreviousObserver)
        previousMatchModel.onMessageError.observe(this, isOnMessageErrorPreviousObserver)
        previousMatchModel.isEmptyList.observe(this, isEmptyListPreviousObserver)
    }

    private fun setupUi() {
        tv_name_league.text = league.name
        Glide.with(this)
            .load(league.image)
            .apply(RequestOptions().override(150,150))
            .into(iv_logo)
        tv_desc.text = league.desc

        rv_classement.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_classement.adapter = nextAdapter
        nextAdapter.notifyDataSetChanged()
        nextAdapter.setOnItemClickCallback(object : OnItemClickCallback<Match> {
            override fun onItemClicked(data: Match) {
                val intent = Intent(activity, MatchDetailActivity::class.java)
                intent.putExtra(MatchDetailActivity.EXTRA_ACTIVITY, this@LeagueDetailFragment::class.java.simpleName)
                intent.putExtra(MatchDetailActivity.EXTRA_MATCH, data)
                startActivity(intent)
            }
        })

        rv_previous_match.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv_previous_match.adapter = previousAdapter
        previousAdapter.notifyDataSetChanged()
        previousAdapter.setOnItemClickCallback(object : OnItemClickCallback<Match> {
            override fun onItemClicked(data: Match) {
                val intent = Intent(activity, MatchDetailActivity::class.java)
                intent.putExtra(MatchDetailActivity.EXTRA_ACTIVITY, this@LeagueDetailFragment::class.java.simpleName)
                intent.putExtra(MatchDetailActivity.EXTRA_MATCH, data)
                startActivity(intent)
            }
        })
    }

    private val isViewLoadingNextObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        pb_fm_classement.visibility = visibility
    }

    private val isOnMessageErrorNextObserver = Observer<String> {
        tv_info_next.text = getString(R.string.txt_fail)
    }

    private val isEmptyListNextObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.INVISIBLE
        tv_info_next.text = getString(R.string.txt_no_data)
        tv_info_next.visibility = visibility
    }

    private val isViewLoadingPreviousObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        pb_fm_previous_match.visibility = visibility
    }

    private val isOnMessageErrorPreviousObserver = Observer<String> {
        tv_info_previous.text = getString(R.string.txt_fail)
    }

    private val isEmptyListPreviousObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.INVISIBLE
        tv_info_previous.text = getString(R.string.txt_no_data)
        tv_info_previous.visibility = visibility
    }

}
