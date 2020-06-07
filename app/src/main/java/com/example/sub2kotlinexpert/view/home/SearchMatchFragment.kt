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
import com.example.sub2kotlinexpert.adapter.MatchAdapter
import com.example.sub2kotlinexpert.adapter.OnItemClickCallback
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.view.matchdetail.MatchDetailActivity
import com.example.sub2kotlinexpert.viewmodel.home.SearchMatchViewModel
import kotlinx.android.synthetic.main.fragment_search_match.*

class SearchMatchFragment : Fragment() {

    private lateinit var adapter: MatchAdapter
    private lateinit var matchModel: SearchMatchViewModel
    private var searchText: String? = null
    private var listMatch = ArrayList<Match>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MatchAdapter()
        matchModel = ViewModelProvider(this, SearchMatchViewModel.viewModelProvider).get(
            SearchMatchViewModel::class.java
        )
        return inflater.inflate(R.layout.fragment_search_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUi()

    }

    private fun setupViewModel() {
        matchModel.getSearchMatch().observe(viewLifecycleOwner, Observer {
            listMatch = it
            adapter.setListMatches(listMatch)
        })
        matchModel.isViewLoading.observe(this, isViewLoadingObserver)
        matchModel.onMessageError.observe(this, isOnMessageErrorObserver)
        matchModel.isEmptyList.observe(this, isEmptyListObserver)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        pb_fm_search_match.visibility = visibility
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
        rv_search_match.layoutManager = LinearLayoutManager(activity)
        rv_search_match.adapter = adapter
        adapter.setOnItemClickCallback(object : OnItemClickCallback<Match> {
            override fun onItemClicked(data: Match) {
                val intent = Intent(activity, MatchDetailActivity::class.java)
                intent.putExtra(
                    MatchDetailActivity.EXTRA_ACTIVITY,
                    this@SearchMatchFragment::class.java.simpleName
                )
                intent.putExtra(MatchDetailActivity.EXTRA_MATCH, data)
                startActivity(intent)
            }
        })
    }

    fun getDataList(text: String) {
        if (text.isNotEmpty()) {
            listMatch.clear()
            adapter.setListMatches(listMatch)
            searchText = text
            matchModel.setSearchMatch(text)
        }
    }
}
