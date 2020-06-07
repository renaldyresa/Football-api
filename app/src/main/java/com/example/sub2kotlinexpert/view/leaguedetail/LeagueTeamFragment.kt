package com.example.sub2kotlinexpert.view.leaguedetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.OnItemClickCallback
import com.example.sub2kotlinexpert.adapter.SectionPagerAdapter
import com.example.sub2kotlinexpert.adapter.TeamAdapter
import com.example.sub2kotlinexpert.entity.Team
import com.example.sub2kotlinexpert.view.teamdetail.TeamDetailActivity
import com.example.sub2kotlinexpert.viewmodel.leaguedetail.LeagueTeamViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_league_previous_match.*

class LeagueTeamFragment : Fragment() {

    private lateinit var adapter: TeamAdapter

    private lateinit var matchModel: LeagueTeamViewModel
    private lateinit var snackBar: Snackbar
    private lateinit var idLeague: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = TeamAdapter()
        matchModel = ViewModelProvider(this, LeagueTeamViewModel.viewModelProvider).get(
            LeagueTeamViewModel::class.java)
        return inflater.inflate(R.layout.fragment_league_previous_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        idLeague = arguments?.getString(SectionPagerAdapter.EXTRA_ID_LEAGUE) as String
        snackBar = Snackbar.make(previous_match_fragment, "failed load data", Snackbar.LENGTH_INDEFINITE)
    }

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupViewModel(){
        matchModel.setTeam(idLeague)
        matchModel.getTeam().observe(this, Observer {
            adapter.setListTeam(it)
        })
        matchModel.isViewLoading.observe(this, isViewLoadingObserver)
        matchModel.onMessageError.observe(this, isOnMessageErrorObserver)
        matchModel.isEmptyList.observe(this, isEmptyListObserver)
    }

    private fun setupUi() {
        rv_previous_match.layoutManager = LinearLayoutManager(activity)
        rv_previous_match.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : OnItemClickCallback<Team> {
            override fun onItemClicked(data: Team) {
                val intent = Intent(activity, TeamDetailActivity::class.java)
                intent.putExtra(TeamDetailActivity.EXTRA_ID_LEAGUE, data.idTeam)
                startActivity(intent)
            }
        })
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        pb_fm_previous_match.visibility = visibility
    }

    private val isOnMessageErrorObserver = Observer<String> {
        snackBar.apply {
            setAction(it){
                matchModel.setTeam(idLeague)
                snackBar.dismiss()
            }.show()
        }
    }

    private val isEmptyListObserver = Observer<Boolean> {
        Toast.makeText(this.context, "No Data", Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        if (snackBar.isShown)
            snackBar.dismiss()
    }
}
