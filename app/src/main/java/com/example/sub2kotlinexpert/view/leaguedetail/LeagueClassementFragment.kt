package com.example.sub2kotlinexpert.view.leaguedetail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.adapter.ClassementAdapter
import com.example.sub2kotlinexpert.adapter.OnItemClickCallback
import com.example.sub2kotlinexpert.adapter.SectionPagerAdapter
import com.example.sub2kotlinexpert.entity.Classement
import com.example.sub2kotlinexpert.model.leaguedetail.ClassementModel
import com.example.sub2kotlinexpert.model.logo.TeamLogoModel
import com.example.sub2kotlinexpert.view.teamdetail.TeamDetailActivity
import com.example.sub2kotlinexpert.viewmodel.leaguedetail.LeagueClassementViewModel
import kotlinx.android.synthetic.main.fragment_league_classement.*


open class LeagueClassementFragment : Fragment() {

    private lateinit var adapter: ClassementAdapter
    private lateinit var matchModel: LeagueClassementViewModel
    private lateinit var idLeague: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ClassementAdapter()
        matchModel = ViewModelProvider(this,
            LeagueClassementViewModel.viewModelProvider).get(
            LeagueClassementViewModel::class.java)
        return inflater.inflate(R.layout.fragment_league_classement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idLeague = arguments?.getString(SectionPagerAdapter.EXTRA_ID_LEAGUE) as String

        setupUi()
    }

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupViewModel(){
        matchModel.setClassement(idLeague)
        matchModel.getClassement().observe(this, Observer {
            adapter.setListClassement(it)
        })
        matchModel.isViewLoading.observe(this, isViewLoadingObserver)
        matchModel.isEmptyList.observe(this, isEmptyListObserver)
    }

    private fun setupUi() {
        rv_classement.layoutManager = LinearLayoutManager(activity)
        rv_classement.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : OnItemClickCallback<Classement> {
            override fun onItemClicked(data: Classement) {
                val intent = Intent(activity, TeamDetailActivity::class.java)
                intent.putExtra(TeamDetailActivity.EXTRA_ID_LEAGUE, data.idTeam)
                startActivity(intent)
            }
        })
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        pb_fm_classement.visibility = visibility
    }

    private val isEmptyListObserver = Observer<Boolean> {
        Toast.makeText(this.context, "No Data", Toast.LENGTH_LONG).show()
    }


}
