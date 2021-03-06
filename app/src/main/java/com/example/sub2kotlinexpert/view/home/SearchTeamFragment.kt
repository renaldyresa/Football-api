package com.example.sub2kotlinexpert.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.OnItemClickCallback
import com.example.sub2kotlinexpert.adapter.TeamAdapter
import com.example.sub2kotlinexpert.entity.Team
import com.example.sub2kotlinexpert.view.teamdetail.TeamDetailActivity
import com.example.sub2kotlinexpert.viewmodel.home.SearchTeamViewModel
import kotlinx.android.synthetic.main.fragment_search_team.*

class SearchTeamFragment : Fragment() {

    private lateinit var adapter: TeamAdapter
    private lateinit var matchModel: SearchTeamViewModel
    private var searchText: String? = null
    private var listMatch = ArrayList<Team>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = TeamAdapter()
        matchModel = ViewModelProvider(this, SearchTeamViewModel.viewModelProvider).get(
            SearchTeamViewModel::class.java
        )
        return inflater.inflate(R.layout.fragment_search_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUi()
    }

    private fun setupViewModel() {
        matchModel.getSearchTeam().observe(viewLifecycleOwner, Observer {
            listMatch = it
            adapter.setListTeam(listMatch)
        })
        matchModel.isViewLoading.observe(this, isViewLoadingObserver)
        matchModel.onMessageError.observe(this, isOnMessageErrorObserver)
        matchModel.isEmptyList.observe(this, isEmptyListObserver)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        pb_fm_search_team.visibility = visibility
    }

    private val isOnMessageErrorObserver = Observer<String> {
        tv_info.text = getString(R.string.txt_fail)
        tv_info.visibility = View.VISIBLE
    }

    private val isEmptyListObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        tv_info.text = getString(R.string.txt_no_data)
        tv_info.visibility = visibility
    }

    private fun setupUi() {
        rv_search_team.layoutManager = LinearLayoutManager(activity)
        rv_search_team.adapter = adapter
        adapter.setOnItemClickCallback(object : OnItemClickCallback<Team> {
            override fun onItemClicked(data : Team) {
                val intent = Intent(activity, TeamDetailActivity::class.java)
                intent.putExtra(TeamDetailActivity.EXTRA_ID_LEAGUE, data.idTeam)
                startActivity(intent)
            }
        })
    }

    fun getDataList(text: String) {
        if (text.isNotEmpty()) {
            listMatch.clear()
            adapter.setListTeam(listMatch)
            searchText = text
            matchModel.setSearchTeam(text)
        }
    }

}
